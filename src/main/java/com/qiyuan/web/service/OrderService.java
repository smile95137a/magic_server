package com.qiyuan.web.service;

import com.qiyuan.security.config.ImagePathMappingConfig;
import com.qiyuan.security.exception.ApiException;
import com.qiyuan.web.dao.*;
import com.qiyuan.web.dto.CreateOrderItem;
import com.qiyuan.web.dto.OrderItemVO;
import com.qiyuan.web.dto.request.*;
import com.qiyuan.web.dto.response.*;
import com.qiyuan.web.entity.*;
import com.qiyuan.web.entity.example.OrderItemExample;
import com.qiyuan.web.entity.example.OrdersExample;
import com.qiyuan.web.entity.example.ShippingMethodExample;
import com.qiyuan.web.enums.*;
import com.qiyuan.web.util.DateUtil;
import com.qiyuan.web.util.RandomGenerator;
import com.qiyuan.web.util.SecurityUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    private final OrdersMapper ordersMapper;
    private final OrderItemMapper orderItemMapper;
    private final ShippingMethodMapper shippingMethodMapper;
    private final UserMapper userMapper;
    private final ProductMapper productMapper;
    private final PaymentTransactionMapper paymentTransactionMapper;
    private final PaymentService paymentService;
    private final StockService stockService;
    private final ImagePathMappingConfig mappingConfig;
    private final InvoiceService invoiceService;

    public OrderService(OrdersMapper ordersMapper, OrderItemMapper orderItemMapper, ShippingMethodMapper shippingMethodMapper, UserMapper userMapper, ProductMapper productMapper, PaymentTransactionMapper paymentTransactionMapper, PaymentService paymentService, StockService stockService, ImagePathMappingConfig mappingConfig, InvoiceService invoiceService) {
        this.ordersMapper = ordersMapper;
        this.orderItemMapper = orderItemMapper;
        this.shippingMethodMapper = shippingMethodMapper;
        this.userMapper = userMapper;
        this.productMapper = productMapper;
        this.paymentTransactionMapper = paymentTransactionMapper;
        this.paymentService = paymentService;
        this.stockService = stockService;
        this.mappingConfig = mappingConfig;
        this.invoiceService = invoiceService;
    }

    @Transactional
    public CreateOrderResponse createOrder(CreateOrderRequest request) {
        List<CreateOrderItem> items = request.getItems();
        if (items == null || items.isEmpty()) throw new ApiException("請選擇購買商品");

        // 1. 取得登入會員
        String currentUsername = SecurityUtils.getCurrentUsername();
        User user = userMapper.selectByUsername(currentUsername);
        if (user == null) throw new ApiException("會員不存在");

        Date currentDate = DateUtil.getCurrentDate();
        String orderId = RandomGenerator.getUUID().toLowerCase(Locale.ROOT);

        BigDecimal total = BigDecimal.ZERO;
        // 2. 處理每個商品（驗證 + 扣庫存 + 建明細）
        for (CreateOrderItem item : items) {
            Product product = productMapper.selectByPrimaryKey(item.getProductId());
            if (product == null || !Boolean.TRUE.equals(product.getStatus()))
                throw new ApiException(item.getProductId() + " 商品不存在或已下架");

            int needQty = item.getQuantity() == null ? 1 : item.getQuantity();
            if (product.getStock() == null || product.getStock() < needQty)
                throw new ApiException("[" + product.getName() + "] 庫存不足");

            // 扣庫存
            stockService.reserveStock(product.getId(), needQty, null);

            // 計算金額
            BigDecimal unitPrice = product.getSpecialPrice();
            BigDecimal subtotal = unitPrice.multiply(BigDecimal.valueOf(needQty));
            total = total.add(subtotal);

            // 建立明細
            OrderItem detail = OrderItem.builder()
                    .orderId(orderId)
                    .productId(product.getId())
                    .productName(product.getName())
                    .unitPrice(product.getOriginalPrice())
                    .quantity(needQty)
                    .specialPrice(product.getSpecialPrice())
                    .subtotal(subtotal)
                    .createTime(currentDate)
                    .build();
            orderItemMapper.insertSelective(detail);
        }

        // 3. 決定收件人資訊
        String recipientName = null, recipientPhone = null, recipientAddress = null, storeId = null, storeName = null;
        ShippingMethod shippingMethod = shippingMethodMapper.selectByPrimaryKey(request.getShippingMethodId());
        // 宅配
        if (StringUtils.equals("SF_EXPRESS", shippingMethod.getCode())) {
            HomeDeliveryRecipientInfo r = request.getHomeDeliveryRecipient();
            if (r == null) throw new ApiException("宅配需填寫收件人資訊");
            recipientName = r.getName();
            recipientPhone = r.getPhone();
            recipientAddress = r.getAddress();
        } else {
            // 超商
            StorePickupRecipientInfo r = request.getStorePickupRecipient();
            if (r == null) throw new ApiException("超商取貨需填寫收件人資訊");
            recipientName = r.getRecipientName();
            recipientPhone = r.getPhone();
            recipientAddress = r.getStoreAddress();
            storeId = r.getStoreId();
            storeName = r.getStoreName();
        }
        total = total.add(BigDecimal.valueOf(shippingMethod.getFee()));

        // 4. 建立訂單主表
        String paymentId = orderId.substring(0, 25);
        Orders order = Orders.builder()
                .id(orderId)
                .externalOrderNo(paymentId)
                .userId(user.getId())
                .totalAmount(total)
                .paid(false)
                .status(OrderStatus.CREATED.getValue())
                .shippingMethodId(request.getShippingMethodId())
                .invoiceType(request.getInvoiceType())
                .invoiceTarget(request.getInvoiceTarget())
                .recipientName(recipientName)
                .recipientPhone(recipientPhone)
                .recipientAddress(recipientAddress)
                .remark(request.getRemark())
                .createTime(currentDate)
                .updateTime(currentDate)
                .storeId(storeId)
                .storeName(storeName)
                .build();
        ordersMapper.insertSelective(order);

        // 建立金流單
        PaymentTransaction tx = PaymentTransaction.builder()
                .id(paymentId)
                .userId(user.getId())
                .sourceType(SourceTypeEnum.REAL.getCode())
                .amount(total)
                .createTime(currentDate)
                .status(OrderStatus.CREATED.getValue())
                .payMethod(request.getPayMethod())
                .build();

        paymentTransactionMapper.insertSelective(tx);
        
        return CreateOrderResponse.builder()
                .orderId(orderId)
                .externalOrderNo(paymentId)
                .totalAmount(total)
                .status(OrderStatus.CREATED.getValue())
                .paymentStatus(OrderStatus.CREATED.getLabel())
                .payMethod(request.getPayMethod())
                .shippingMethod(order.getShippingMethodId())
//                .paymentUrl(paymentResult.getPaymentUrl())
                .createTime(DateFormatUtils.format(currentDate, "yyyy-MM-dd HH:mm:ss"))
                .build();
    }


    public List<OrderItem> getOrderItemsByOrderId(String orderId) {
        OrderItemExample example = new OrderItemExample();
        example.createCriteria().andOrderIdEqualTo(orderId);
        return orderItemMapper.selectByExample(example);
    }

    // 2. 會員查詢自己的訂單列表
    public List<OrderVO> getOrderList(QueryOrderRequest request) {
        String currentUsername = SecurityUtils.getCurrentUsername();
        User user = userMapper.selectByUsername(currentUsername);

        OrdersExample example = new OrdersExample();
        OrdersExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(user.getId());
        if (request.getStatus() != null) {
            criteria.andStatusEqualTo(request.getStatus());
        }
        if (request.getStartTime() != null) {
            criteria.andCreateTimeGreaterThanOrEqualTo(request.getStartTime());
        }
        if (request.getEndTime() != null) {
            criteria.andCreateTimeLessThanOrEqualTo(DateUtil.getEndOfDate(request.getEndTime()));
        }
        // 處理分頁
        int page = (request.getPage() != null && request.getPage() > 0) ? request.getPage() : 1;
        int size = (request.getSize() != null && request.getSize() > 0) ? request.getSize() : 20;
        int offset = (page - 1) * size;
        example.setOrderByClause("create_time desc");

        List<Orders> orderList = ordersMapper.selectByExampleWithPage(example, offset, size);
        List<OrderVO> voList = new ArrayList<>();
        for (Orders o : orderList) {
            voList.add(toOrderVO(o));
        }
        return voList;
    }


    public OrderDetailVO getOrderDetail(String orderId) {
        String currentUsername = SecurityUtils.getCurrentUsername();
        User user = userMapper.selectByUsername(currentUsername);

        Orders order = ordersMapper.selectByPrimaryKey(orderId);
        if (order == null || !order.getUserId().equals(user.getId())) {
            throw new ApiException("訂單不存在或無權限查詢");
        }
        List<OrderItem> items = getOrderItemsByOrderId(orderId);
        OrderDetailVO vo = toOrderDetailVO(order, items);
        return vo;
    }

    @Transactional
    public void cancelOrder(CancelOrderRequest request) {
        String currentUsername = SecurityUtils.getCurrentUsername();
        User user = userMapper.selectByUsername(currentUsername);

        Orders order = ordersMapper.selectByPrimaryKey(request.getOrderId());
        if (order == null || !order.getUserId().equals(user.getId())) {
            throw new RuntimeException("訂單不存在或無權限操作");
        }
        if (!OrderStatus.CREATED.getValue().equals(order.getStatus())) {
            throw new RuntimeException("僅能取消未付款訂單");
        }

        Orders update = new Orders();
        update.setId(order.getId());
        update.setStatus(OrderStatus.CANCELLED.getValue());
        update.setRemark(request.getRemark());
        update.setUpdateTime(new Date());
        ordersMapper.updateByPrimaryKeySelective(update);

        OrderItemExample ie = new OrderItemExample();
        ie.createCriteria().andOrderIdEqualTo(order.getId());
        List<OrderItem> itemList = orderItemMapper.selectByExample(ie);
        for (OrderItem item : itemList) {
            stockService.releaseReservedStock(item.getProductId(), item.getQuantity(), null);
        }
    }

    // 5. 物流方式列表
    public List<ShippingMethodVO> getShippingMethodList() {
        ShippingMethodExample example = new ShippingMethodExample();
        example.createCriteria().andStatusEqualTo(true);
        example.setOrderByClause("sort asc, create_time desc");
        List<ShippingMethod> list = shippingMethodMapper.selectByExample(example);
        List<ShippingMethodVO> voList = new ArrayList<>();
        for (ShippingMethod method : list) {
            ShippingMethodVO vo = new ShippingMethodVO();
            vo.setId(method.getId());
            vo.setCode(method.getCode());
            vo.setName(method.getName());
            vo.setDescription(method.getDescription());
            vo.setFee(method.getFee());
            vo.setStatus(method.getStatus());
            vo.setSort(method.getSort());
            voList.add(vo);
        }
        return voList;
    }



    private OrderVO toOrderVO(Orders o) {
        ShippingMethod shippingMethod = shippingMethodMapper.selectByPrimaryKey(o.getShippingMethodId());
        OrderVO vo = new OrderVO();
        vo.setId(o.getId());
        vo.setTotalAmount(o.getTotalAmount());
        vo.setStatus(OrderStatus.findByValue(o.getStatus().toLowerCase(Locale.ROOT)).getLabel());
        vo.setPaymentStatus(PaymentEnum.fromBoolean(o.getPaid()).getLabel());
        vo.setShippingMethod(shippingMethod.getName());
        vo.setTrackingNo(o.getTrackingNo());
        vo.setCreateTime(o.getCreateTime());
        vo.setUpdateTime(o.getUpdateTime());
        return vo;
    }

    private OrderDetailVO toOrderDetailVO(Orders o, List<OrderItem> items) {
        ShippingMethod shippingMethod = shippingMethodMapper.selectByPrimaryKey(o.getShippingMethodId());

        OrderDetailVO vo = new OrderDetailVO();
        vo.setId(o.getId());
        vo.setTotalAmount(o.getTotalAmount());
        vo.setStatus(OrderStatus.findByValue(o.getStatus().toLowerCase(Locale.ROOT)).getLabel());
        vo.setPaymentStatus(PaymentEnum.fromBoolean(o.getPaid()).getLabel());
        vo.setShippingMethod(shippingMethod.getName());
        vo.setInvoiceType(o.getInvoiceType());
        vo.setInvoiceTarget(o.getInvoiceTarget());
        vo.setRecipientName(o.getRecipientName());
        vo.setRecipientPhone(o.getRecipientPhone());
        vo.setRecipientAddress(o.getRecipientAddress());
        vo.setRemark(o.getRemark());
        vo.setTrackingNo(o.getTrackingNo());
        vo.setCreateTime(o.getCreateTime());
        vo.setUpdateTime(o.getUpdateTime());
        List<OrderItemVO> itemVOList = new ArrayList<>();

        for (OrderItem i : items) {
            OrderItemVO itemVO = new OrderItemVO();
            Product p = productMapper.selectByPrimaryKey(i.getProductId());
            itemVO.setProductId(i.getProductId());
            itemVO.setProductName(i.getProductName());
            itemVO.setUnitPrice(i.getUnitPrice());
            itemVO.setQuantity(i.getQuantity());
            itemVO.setSubtotal(i.getSubtotal());
            itemVO.setProductUrl(buildMainImageUrl(p));
            itemVOList.add(itemVO);
        }
        vo.setItems(itemVOList);
        return vo;
    }

    private String buildMainImageUrl(Product p) {
        if (p.getMainImage() == null) return null;
        String prefix = mappingConfig.getUrlPrefix("product") + p.getId() + "/" + ProductImageType.MAIN.getFolder() + "/";
        return prefix + p.getMainImage();
    }

    @Transactional
    public void markAsPaid(PaySuccessRequest r) {
        String paymentId = r.getExternalOrderNo();
        String providerOrderNo = r.getProviderOrderNo();

        PaymentTransaction tx = paymentTransactionMapper.selectByPrimaryKey(paymentId);
        if (tx == null) throw new ApiException("查無金流紀錄");
        Orders order = ordersMapper.selectByExternalOrderNo(paymentId);

        if (order == null)  throw new ApiException("查無訂單");

        if (!OrderStatus.CREATED.getValue().equalsIgnoreCase(tx.getStatus())) {
            logger.info("付款已處理或非等待狀態, status={}, id={}", tx.getStatus(), tx.getId());
            return;
        }



        // 2. 更新付款狀態為成功
        tx.setStatus(OrderStatus.PAID.getValue());
        tx.setProviderOrderNo(providerOrderNo);
        tx.setPayMethod(PayMethodEnum.CREDIT_CARD.getCode());
        tx.setUpdateTime(new Date());
        paymentTransactionMapper.updateByPrimaryKey(tx);

        // 更新訂單狀態
        Orders update = new Orders();
        update.setId(order.getId());
        update.setPaid(true);
        update.setStatus(OrderStatus.PAID.getValue());
        update.setUpdateTime(new Date());
        ordersMapper.updateByPrimaryKeySelective(update);

        try {
            // 成立發票
            invoiceService.issueInvoice(paymentId);
        } catch (Exception e) {
            logger.error("商城訂單 {} 金流單號 {} 付款完成，但發票開立失敗!!", order.getId(), paymentId);
        }
    }


}
