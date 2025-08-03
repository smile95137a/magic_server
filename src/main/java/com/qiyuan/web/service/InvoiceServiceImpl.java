package com.qiyuan.web.service;

import com.qiyuan.security.exception.ApiException;
import com.qiyuan.web.config.GomypayEinvoiceProperties;
import com.qiyuan.web.dao.*;
import com.qiyuan.web.dto.InvoiceValidationDto;
import com.qiyuan.web.dto.request.IssueInvoiceDto;
import com.qiyuan.web.dto.response.*;
import com.qiyuan.web.entity.*;
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
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {

    private static final Logger logger = LoggerFactory.getLogger(InvoiceServiceImpl.class);

    private final InvoiceMapper invoiceMapper;
    private final OrdersMapper ordersMapper;
    private final OrderItemMapper orderItemMapper;
    private final VirtualOrdersMapper virtualOrdersMapper;
    private final VirtualOrderItemMapper virtualOrderItemMapper;
    private final PaymentTransactionMapper paymentTransactionMapper;
    private final GomypayEinvoiceProperties einvoiceProps;
    private final RestTemplate restTemplate;

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
    @Transactional
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
            ResponseEntity<IssueInvoiceResponse> response =
                    restTemplate.postForEntity(addUrl, invoiceDto, IssueInvoiceResponse.class);

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

        if (invoiceType.equals(InvoiceType.COMPANY)) {
            invoice.setBuyerIdentifier(target.getInvoiceTarget()); // 統編
            invoice.setBuyerName("");  // 買方名稱
        } else if (invoiceType.equals(InvoiceType.DONATION)) {
            invoice.setNPOBAN(target.getInvoiceTarget());
        } else if  (invoiceType.equals(InvoiceType.MOBILE)) {
            invoice.setCarrierType("3J0002");
            invoice.setCarrierId1(target.getInvoiceTarget());
        }

        OrderItemExample oie = new OrderItemExample();
        oie.createCriteria().andOrderIdEqualTo(target.getId());
        List<OrderItem> orderItems = orderItemMapper.selectByExample(oie);

        if (orderItems == null || orderItems.size() == 0) throw new ApiException("發票開立失敗，查無訂單");
        List<IssueInvoiceDto.InvoiceItemDto> items = orderItems.stream().map(item -> IssueInvoiceDto.InvoiceItemDto.builder()
                    .Amount(item.getSubtotal().intValue())
                    .Quantity(item.getQuantity())
                    .Description(item.getProductName())
                    .UnitPrice(item.getSpecialPrice())
                    .build()
        ).collect(Collectors.toList());

        invoice.setITEM(items);
        int total = items.stream()
                .mapToInt(IssueInvoiceDto.InvoiceItemDto::getAmount)
                .sum();

        invoice.setFreeTaxSalesAmount(0);
        invoice.setZeroTaxSalesAmount(0);
        invoice.setSalesAmount(0);
        invoice.setTaxType("1");
        invoice.setTaxRate(BigDecimal.valueOf(0.05));
        invoice.setTaxAmount(0);
        invoice.setTotalAmount(total);

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

        if (invoiceType.equals(InvoiceType.COMPANY)) {
            invoice.setBuyerIdentifier(target.getInvoiceTarget()); // 統編
            invoice.setBuyerName("");  // 買方名稱
        } else if (invoiceType.equals(InvoiceType.DONATION)) {
            invoice.setNPOBAN(target.getInvoiceTarget());
        } else if  (invoiceType.equals(InvoiceType.MOBILE)) {
            invoice.setCarrierType("3J0002");
            invoice.setCarrierId1(target.getInvoiceTarget());
        }

        VirtualOrderItemExample vie = new VirtualOrderItemExample();
        vie.createCriteria().andOrderIdEqualTo(target.getId());
        List<VirtualOrderItem> virtualOrderItems = virtualOrderItemMapper.selectByExample(vie);

        if (virtualOrderItems == null || virtualOrderItems.size() == 0) throw new ApiException("發票開立失敗，查無訂單");
        List<IssueInvoiceDto.InvoiceItemDto> items = virtualOrderItems.stream().map(item -> IssueInvoiceDto.InvoiceItemDto.builder()
                .Amount(item.getAmount().intValue())
                .Quantity(item.getQuantity())
                .Description(item.getDescription())
                .UnitPrice(item.getUnitPrice())
                .build()
        ).collect(Collectors.toList());

        invoice.setITEM(items);

        int total = items.stream()
                .mapToInt(IssueInvoiceDto.InvoiceItemDto::getAmount)
                .sum();

        invoice.setFreeTaxSalesAmount(0);
        invoice.setZeroTaxSalesAmount(0);
        invoice.setSalesAmount(0);
        invoice.setTaxType("1");
        invoice.setTaxRate(BigDecimal.valueOf(0.05));
        invoice.setTaxAmount(0);
        invoice.setTotalAmount(total);

        return invoice;
    }

}
