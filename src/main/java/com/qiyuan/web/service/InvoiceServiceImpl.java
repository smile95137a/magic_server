package com.qiyuan.web.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.util.Timeout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.qiyuan.security.exception.ApiException;
import com.qiyuan.web.config.GomypayEinvoiceProperties;
import com.qiyuan.web.dao.InvoiceMapper;
import com.qiyuan.web.dao.OrderItemMapper;
import com.qiyuan.web.dao.OrdersMapper;
import com.qiyuan.web.dao.PaymentTransactionMapper;
import com.qiyuan.web.dao.UserMapper;
import com.qiyuan.web.dao.VirtualOrderItemMapper;
import com.qiyuan.web.dao.VirtualOrdersMapper;
import com.qiyuan.web.dto.InvoiceValidationDto;
import com.qiyuan.web.dto.request.IssueInvoiceDto;
import com.qiyuan.web.dto.response.InvoiceValidationResult;
import com.qiyuan.web.dto.response.InvoiceVo;
import com.qiyuan.web.dto.response.IssueInvoiceResponse;
import com.qiyuan.web.entity.Invoice;
import com.qiyuan.web.entity.OrderItem;
import com.qiyuan.web.entity.Orders;
import com.qiyuan.web.entity.PaymentTransaction;
import com.qiyuan.web.entity.User;
import com.qiyuan.web.entity.VirtualOrderItem;
import com.qiyuan.web.entity.VirtualOrders;
import com.qiyuan.web.entity.example.OrderItemExample;
import com.qiyuan.web.entity.example.OrdersExample;
import com.qiyuan.web.entity.example.VirtualOrderItemExample;
import com.qiyuan.web.entity.example.VirtualOrdersExample;
import com.qiyuan.web.enums.InvoiceType;
import com.qiyuan.web.enums.OrderStatus;
import com.qiyuan.web.enums.SourceTypeEnum;
import com.qiyuan.web.util.DateUtil;
import com.qiyuan.web.util.RandomGenerator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {

    private static final Logger logger = LoggerFactory.getLogger(InvoiceServiceImpl.class);

    private final InvoiceMapper invoiceMapper;
    private final UserMapper userMapper;
    private final OrdersMapper ordersMapper;
    private final OrderItemMapper orderItemMapper;
    private final VirtualOrdersMapper virtualOrdersMapper;
    private final VirtualOrderItemMapper virtualOrderItemMapper;
    private final PaymentTransactionMapper paymentTransactionMapper;
    private final GomypayEinvoiceProperties einvoiceProps;
    private final RestTemplate restTemplate;
    
    private RestTemplate createNoMtlsRestTemplate() {
        RequestConfig config = RequestConfig.custom()
            .setConnectTimeout(Timeout.ofSeconds(10))          // TCP 連線建立超時
            .setResponseTimeout(Timeout.ofSeconds(10))         // 整個回應讀取超時
            .setConnectionRequestTimeout(Timeout.ofSeconds(10))// 從連線池取連線超時
            .build();

        CloseableHttpClient httpClient = HttpClients.custom()
            .setDefaultRequestConfig(config)
            .build();

        HttpComponentsClientHttpRequestFactory factory =
            new HttpComponentsClientHttpRequestFactory(httpClient);

        return new RestTemplate(factory);
    }

    @Override
    public boolean validateInfo(InvoiceType type, String code) {
        InvoiceValidationDto dto = InvoiceValidationDto.builder()
                .Code(code)
                .check_pwd(einvoiceProps.getPwd())
                .user_id(einvoiceProps.getCustomerNo())
                .Type(type == InvoiceType.DONATION ? "2" : "1")
                .build();

        String checkInfoUrl = einvoiceProps.getCheckInfoUrl();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<InvoiceValidationDto> request = new HttpEntity<>(dto, headers);

        try {
            ResponseEntity<InvoiceValidationResult> response =
                    restTemplate.postForEntity(checkInfoUrl, request, InvoiceValidationResult.class);

            InvoiceValidationResult result = response.getBody();

            if (!response.getStatusCode().is2xxSuccessful() || result == null) {
                logger.error("發票驗證失敗，HTTP狀態碼: {}, 回傳為空或格式錯誤", response.getStatusCode());
                throw new ApiException("發票驗證失敗，無法取得有效回應");
            }

            if ("1".equals(result.getResult())) {
                return true;
            } else {
                logger.warn("發票驗證未通過，訊息: {}", result.getMessage());
                throw new ApiException(result.getMessage());
            }

        } catch (Exception e) {
            logger.error("呼叫發票驗證 API 發生錯誤: {}", e.getMessage(), e);
            throw new ApiException("發票驗證失敗：" + e.getMessage());
        }
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public InvoiceVo issueInvoice(String paymentId) {
        PaymentTransaction tx = paymentTransactionMapper.selectByPrimaryKey(paymentId);
        SourceTypeEnum typeEnum = SourceTypeEnum.fromCode(tx.getSourceType());
        OrderStatus status = OrderStatus.findByValue(tx.getStatus());
        if (!status.equals(OrderStatus.PAID)) {
            throw new ApiException("發票開立失敗，訂單狀態為: " + status.getLabel());
        }


        String addUrl = einvoiceProps.getIssueUrl();
        IssueInvoiceDto invoiceDto = null;
        String orderId = null;
        // 實體商品
        if (typeEnum.equals(SourceTypeEnum.REAL)) {
            OrdersExample oe = new OrdersExample();
            oe.createCriteria().andExternalOrderNoEqualTo(tx.getId());
            List<Orders> orders = ordersMapper.selectByExample(oe);
            if (orders == null || orders.size() == 0) throw new ApiException("發票開立失敗，查無訂單");
            Orders target = orders.get(0);
            orderId = target.getId();
            invoiceDto = this.getRealProductOrderInvoice(target);

        } else {
            // 虛擬商品
            VirtualOrdersExample ve = new VirtualOrdersExample();
            ve.createCriteria().andExternalOrderNoEqualTo(tx.getId());
            List<VirtualOrders> virtualOrders = virtualOrdersMapper.selectByExample(ve);
            if (virtualOrders == null || virtualOrders.size() == 0) throw new ApiException("發票開立失敗，查無訂單");

            VirtualOrders target = virtualOrders.get(0);
            orderId = target.getId();
            invoiceDto = this.getVirtualProductOrderInvoice(target);
        }

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(List.of(MediaType.APPLICATION_JSON));

            HttpEntity<IssueInvoiceDto> request = new HttpEntity<>(invoiceDto, headers);

            
            RestTemplate noMtlsRt = createNoMtlsRestTemplate();
            ResponseEntity<IssueInvoiceResponse> response =
                    noMtlsRt.postForEntity(addUrl, request, IssueInvoiceResponse.class);

            IssueInvoiceResponse result = response.getBody();

            if (!response.getStatusCode().is2xxSuccessful() || result == null) {
                logger.error("發票開立失敗，HTTP狀態碼: {}, 回傳為空或格式錯誤", response.getStatusCode());
                throw new ApiException("發票驗證失敗，無法取得有效回應");
            }

            if ("1".equals(result.getResult())) {
                String dateStr = result.getInvoiceDate() + " " + result.getInvoiceTime();
                SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Invoice invoice = Invoice.builder()
                        .id(RandomGenerator.getUUID().toLowerCase(Locale.ROOT))
                        .orderId(orderId)
                        .externalOrderNo(paymentId)
                        .invoiceNumber(result.getInvoiceNumber())
                        .invoiceDate(format.parse(dateStr))
                        .randomNumber(result.getRandomNumber())
                        .tax(BigDecimal.valueOf(Integer.valueOf(result.getTaxAmount())))
                        .amount(BigDecimal.valueOf(Long.valueOf(result.getAmount())))
                        .printMark("N")
                        .status(OrderStatus.CREATED.getValue())
                        .responseMsg(result.getMessage())
                        .rawResponse(result.toString())
                        .createTime(new Date())
                        .updateTime(new Date())
                        .build();

                invoiceMapper.insertSelective(invoice);

                return InvoiceVo.builder()
                        .success(true)
                        .invoiceNumber(result.getInvoiceNumber())
                        .randomNumber(result.getRandomNumber())
                        .build();
            } else {
                logger.error("發票開立失敗，訊息: {}", result.getMessage());
                logger.error(result.toString());
                throw new ApiException(result.getMessage());
            }

        } catch (Exception e) {
            logger.error("呼叫發票開立API 發生錯誤: {}", e.getMessage(), e);
            throw new ApiException("發票驗證失敗：" + e.getMessage());
        }
    }


    private IssueInvoiceDto getRealProductOrderInvoice(Orders target) {
        InvoiceType invoiceType = InvoiceType.fromValue(target.getInvoiceType());
        // 第三方測試環境有問題，只能檢核手機條碼
        if (invoiceType.equals(InvoiceType.MOBILE)) {
            validateInfo(invoiceType, target.getInvoiceTarget());
        }

        Date currentDate = DateUtil.getCurrentDate();

        IssueInvoiceDto invoice = IssueInvoiceDto.builder()
                .user_id(einvoiceProps.getCustomerNo())
                .check_pwd(einvoiceProps.getPwd())
                .InvoiceDate(DateFormatUtils.format(currentDate, "yyyy/MM/dd"))
                .InvoiceTime(DateFormatUtils.format(currentDate, "HH:mm:ss"))
                .PrintMark("N")
                .build();

        String userId = target.getUserId();
        User user = userMapper.selectByPrimaryKey(userId);
        invoice.setBuyerEmailAddress(user.getEmail());
        invoice.setTaxRate("0.05");
        if (invoiceType.equals(InvoiceType.COMPANY)) {
            invoice.setBuyerIdentifier(target.getInvoiceTarget()); // 統編
        } else if (invoiceType.equals(InvoiceType.DONATION)) {
            invoice.setNPOBAN(target.getInvoiceTarget());
        } else if  (invoiceType.equals(InvoiceType.MOBILE)) {
            invoice.setCarrierType("3J0002");
            invoice.setCarrierId1(target.getInvoiceTarget());
            invoice.setCarrierId2(target.getInvoiceTarget());
        }

        OrderItemExample oie = new OrderItemExample();
        oie.createCriteria().andOrderIdEqualTo(target.getId());
        List<OrderItem> orderItems = orderItemMapper.selectByExample(oie);

        if (orderItems == null || orderItems.size() == 0) throw new ApiException("發票開立失敗，查無訂單");
        List<IssueInvoiceDto.InvoiceItemDto> items = orderItems.stream().map(item -> IssueInvoiceDto.InvoiceItemDto.builder()
                    .Amount(String.valueOf(item.getSubtotal().intValue()))
                    .Quantity(String.valueOf(item.getQuantity()))
                    .Description(item.getProductName())
                    .UnitPrice(String.valueOf(item.getSpecialPrice().intValue()))
                    .build()
        ).collect(Collectors.toList());

        invoice.setITEM(items);
        int total = items.stream()
                .map(IssueInvoiceDto.InvoiceItemDto::getAmount)
                .mapToInt(Integer::parseInt)
                .sum();

        if (invoiceType.equals(InvoiceType.COMPANY)) {
            int tax = (int) (Double.valueOf(total) * 0.05);
            invoice.setTaxAmount(String.valueOf(tax));
            invoice.setSalesAmount(String.valueOf(total - tax));
        } else {
            invoice.setTaxAmount("0");
            invoice.setSalesAmount(String.valueOf(total));
        }

        invoice.setFreeTaxSalesAmount("0");
        invoice.setZeroTaxSalesAmount("0");
        invoice.setTaxType("1");
        invoice.setTotalAmount(String.valueOf(total));

        return invoice;
    }

    private IssueInvoiceDto getVirtualProductOrderInvoice(VirtualOrders target) {
        InvoiceType invoiceType = InvoiceType.fromValue(target.getInvoiceType());
        if (invoiceType.equals(InvoiceType.DONATION) || invoiceType.equals(InvoiceType.MOBILE)) {
            validateInfo(invoiceType, target.getInvoiceTarget());
        }


        Date currentDate = DateUtil.getCurrentDate();

        IssueInvoiceDto invoice = IssueInvoiceDto.builder()
                .user_id(einvoiceProps.getCustomerNo())
                .check_pwd(einvoiceProps.getPwd())
                .InvoiceDate(DateFormatUtils.format(currentDate, "yyyy/MM/dd"))
                .InvoiceTime(DateFormatUtils.format(currentDate, "HH:mm:ss"))
                .PrintMark("N")
                .build();

        String userId = target.getUserId();
        User user = userMapper.selectByPrimaryKey(userId);
        invoice.setBuyerEmailAddress(user.getEmail());
        invoice.setTaxRate("0.05");
        if (invoiceType.equals(InvoiceType.COMPANY)) {
            invoice.setBuyerIdentifier(target.getInvoiceTarget()); // 統編
        } else if (invoiceType.equals(InvoiceType.DONATION)) {
            invoice.setNPOBAN(target.getInvoiceTarget());
        } else if  (invoiceType.equals(InvoiceType.MOBILE)) {
            invoice.setCarrierType("3J0002");
            invoice.setCarrierId1(target.getInvoiceTarget());
            invoice.setCarrierId2(target.getInvoiceTarget());
        }

        VirtualOrderItemExample vie = new VirtualOrderItemExample();
        vie.createCriteria().andOrderIdEqualTo(target.getId());
        List<VirtualOrderItem> virtualOrderItems = virtualOrderItemMapper.selectByExample(vie);

        if (virtualOrderItems == null || virtualOrderItems.size() == 0) throw new ApiException("發票開立失敗，查無訂單");
        List<IssueInvoiceDto.InvoiceItemDto> items = virtualOrderItems.stream().map(item -> IssueInvoiceDto.InvoiceItemDto.builder()
                .Amount(String.valueOf(item.getAmount().intValue()))
                .Quantity(String.valueOf(item.getQuantity()))
                .Description(item.getDescription())
                .UnitPrice(String.valueOf(item.getUnitPrice().intValue()))
                .build()
        ).collect(Collectors.toList());

        invoice.setITEM(items);

        int total = items.stream()
                .map(IssueInvoiceDto.InvoiceItemDto::getAmount)
                .mapToInt(Integer::parseInt)
                .sum();

        if (invoiceType.equals(InvoiceType.COMPANY)) {
            int tax = (int) (Double.valueOf(total) * 0.05);
            invoice.setTaxAmount(String.valueOf(tax));
            invoice.setSalesAmount(String.valueOf(total - tax));
        } else {
            invoice.setTaxAmount("0");
            invoice.setSalesAmount(String.valueOf(total));
        }

        invoice.setFreeTaxSalesAmount("0");
        invoice.setZeroTaxSalesAmount("0");
        invoice.setTaxType("1");
        invoice.setTotalAmount(String.valueOf(total));

        return invoice;
    }

}
