package com.qiyuan.web.service;

import com.qiyuan.security.config.ImagePathMappingConfig;
import com.qiyuan.security.exception.ApiException;
import com.qiyuan.web.controller.front.LogisticsFrontController;
import com.qiyuan.web.dao.*;
import com.qiyuan.web.dto.ExpressCreateResult;
import com.qiyuan.web.dto.OrderItemVO;
import com.qiyuan.web.dto.OrderStatusUpdateItem;
import com.qiyuan.web.dto.SenderInfoDto;
import com.qiyuan.web.dto.request.*;
import com.qiyuan.web.dto.response.*;
import com.qiyuan.web.entity.*;
import com.qiyuan.web.entity.example.OrderItemExample;
import com.qiyuan.web.entity.example.OrdersExample;
import com.qiyuan.web.entity.example.ShippingMethodExample;
import com.qiyuan.web.entity.example.ShippingTrackingExample;
import com.qiyuan.web.enums.OrderStatus;
import com.qiyuan.web.enums.PaymentEnum;
import com.qiyuan.web.enums.ProductImageType;
import com.qiyuan.web.util.DateUtil;
import com.qiyuan.web.util.RandomGenerator;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderAdminService {

    private final OrdersMapper ordersMapper;
    private final OrderItemMapper orderItemMapper;
    private final ShippingMethodMapper shippingMethodMapper;
    private final ShippingTrackingMapper shippingTrackingMapper;
    private final UserMapper userMapper;
    private final StockService stockService;
    private final ImagePathMappingConfig mappingConfig;
    private final ProductMapper productMapper;
    private final GomypayLogisticsService logisticsService;
    private final SystemConfigService systemConfigService;

    public List<OrderQueryResultVO> getOrderList(QueryOrderAdminRequest request) {
        return ordersMapper.listOrderDetailsAdmin(request);
    }

    // 查詢訂單詳情
    public OrderDetailVO getOrderDetail(String orderId) {
        Orders order = ordersMapper.selectByPrimaryKey(orderId);
        if (order == null) {
            throw new ApiException("訂單不存在");
        }
        List<OrderItem> items = getOrderItemsByOrderId(orderId);
        OrderDetailVO orderDetailVO = toOrderDetailVO(order, items);
        orderDetailVO.setTrackingNo(getTrackingNoByOrder(orderId));
        return orderDetailVO;
    }

    // 批次變更訂單狀態
    @Transactional
    public void updateOrderStatusBatch(UpdateOrderStatusBatchRequest request) {
        OrderStatus targetStatus = request.getStatus();

        Map<String, ShippingMethod> shippingMap = new HashMap<>();
        for (OrderStatusUpdateItem update : request.getUpdates()) {
            Orders order = ordersMapper.selectByPrimaryKey(update.getOrderId());
            if (order == null) continue;

            // 查詢訂單明細
            OrderItemExample e = new OrderItemExample();
            e.createCriteria().andOrderIdEqualTo(order.getId());
            List<OrderItem> items = orderItemMapper.selectByExample(e);

            // 處理庫存異動
            if (targetStatus == OrderStatus.SHIPPED) {
            // 出貨：將 reservedStock 扣除（shipReservedStock）
                for (OrderItem item : items) {
                    stockService.shipReservedStock(item.getProductId(), item.getQuantity(), null);
                }
            } else if (targetStatus == OrderStatus.RETURNED) {
            // 退貨
                for (OrderItem item : items) {
                    stockService.releaseReservedStock(item.getProductId(), item.getQuantity(), null);
                }
            }

            Orders record = new Orders();
            record.setId(order.getId());
            record.setStatus(targetStatus.getValue());

            if (update.getRemark() != null) {
                record.setRemark(update.getRemark());
            }
            record.setUpdateTime(DateUtil.getCurrentDate());
            ordersMapper.updateByPrimaryKeySelective(record);
        }
    }

    /**
     * 批次更新訂單 訂單準備中 => 準備出貨中
     * @param request
     */
    @Transactional
    public void markReadyToShip(List<MarkReadyToShipRequest> request) {
        Map<String, ShippingMethod> shippingMap = new HashMap<>();
        SenderInfoDto senderInfo = systemConfigService.getSenderInfo();

        for (MarkReadyToShipRequest item : request) {
            Orders order = ordersMapper.selectByPrimaryKey(item.getOrderId());
            if (order == null) continue;

            // 查詢訂單明細
            OrderItemExample e = new OrderItemExample();
            e.createCriteria().andOrderIdEqualTo(order.getId());
            List<OrderItem> items = orderItemMapper.selectByExample(e);

            // 準備出貨，建立物流單
            String shippingMethodId = order.getShippingMethodId();
            ShippingMethod shippingMethod = null;
            if (shippingMap.containsKey(shippingMethodId)) {
                shippingMethod = shippingMap.get(shippingMethodId);
            } else {
                shippingMethod = shippingMethodMapper.selectByPrimaryKey(shippingMethodId);
                shippingMap.put(shippingMethodId, shippingMethod);
            }

            // 宅配
            String trackingId = null;
            if (StringUtils.equals("SF_EXPRESS",shippingMethod.getCode())) {
                ExpressDeliveryRequest target = ExpressDeliveryRequest.builder()
                        .orderId(item.getOrderId())
                        .declaredValue(order.getTotalAmount())
                        .totalWeight(BigDecimal.valueOf(5)) // 寫死
                        .pickupType(0)
                        .pickupAppointTime(null)
                        .productName("商品")
                        .quantity(items.size())
                        .recipientName(order.getRecipientName())
                        .recipientMobile(order.getRecipientPhone())
                        .recipientAddress(order.getRecipientAddress())
                        .recipientRegion(order.getRecipientCity())
                        .recipientAddress(order.getRecipientAddress())
                        .recipientZipCode(order.getZipCode())
                        .senderAddress(senderInfo.getAddress())
                        .senderName(senderInfo.getName())
                        .senderMobile(senderInfo.getPhone())
                        .senderRegion(senderInfo.getCity())
                        .senderAddress(senderInfo.getAddress())
                        .senderZipCode(senderInfo.getZipcode())
                        .remark(item.getRemark())
                        .build();
                trackingId = logisticsService.createExpressDeliveryOrder(target);
            } else {
                // 超商取貨
                StorePickupRequest target = StorePickupRequest.builder()
                        .orderId(order.getId())
                        .storeId(order.getStoreId())
                        .amount(order.getTotalAmount())
                        .orderAmount(order.getTotalAmount())
                        .senderName(senderInfo.getName())
                        .sendMobilePhone(senderInfo.getPhone())
                        .receiverName(order.getRecipientName())
                        .receiverMobilePhone(order.getRecipientPhone())
                        .StoreCode(shippingMethod.getCode())
                        .shipDate(item.getShippingDate())
                        .build();
                trackingId = logisticsService.createStorePickupOrder(target);
            }

            Orders record = new Orders();
            record.setId(order.getId());
            record.setStatus(OrderStatus.READY_TO_SHIP.getValue());
            if (item.getRemark() != null) {
                record.setRemark(item.getRemark());
            }
            record.setTrackingNo(trackingId);
            record.setUpdateTime(DateUtil.getCurrentDate());
            ordersMapper.updateByPrimaryKeySelective(record);
        }
    }

    @Transactional
    public void saveShippingMethod(ShippingMethodRequest request) {
        ShippingMethod method = shippingMethodMapper.selectByPrimaryKey(request.getId());
        if (method == null) throw new ApiException("物流方式不存在");
        method.setName(request.getName());
        method.setDescription(request.getDescription());
        method.setFee(request.getFee());
        method.setStatus(request.getStatus());
        method.setSort(request.getSort());
        method.setUpdateTime(new Date());
        shippingMethodMapper.updateByPrimaryKeySelective(method);
    }


    public List<ShippingMethodVO> getShippingMethodList() {
        ShippingMethodExample example = new ShippingMethodExample();
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

    public List<OrderItem> getOrderItemsByOrderId(String orderId) {
        OrderItemExample example = new OrderItemExample();
        example.createCriteria().andOrderIdEqualTo(orderId);
        return orderItemMapper.selectByExample(example);
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
            itemVO.setProductId(i.getProductId());
            itemVO.setProductName(i.getProductName());
            itemVO.setUnitPrice(i.getUnitPrice());
            itemVO.setQuantity(i.getQuantity());
            itemVO.setSubtotal(i.getSubtotal());
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
    public DeliveryNoteVO getDeliveryNote(String orderId) {
        Orders order = ordersMapper.selectByPrimaryKey(orderId);
        if (order == null) throw new ApiException("訂單不存在");

        // 取得會員資訊
        User user = userMapper.selectByPrimaryKey(order.getUserId());
        if (user == null) throw new ApiException("查無會員");


        ShippingMethod shippingMethod = shippingMethodMapper.selectByPrimaryKey(order.getShippingMethodId());

        HomeDeliveryRecipientInfo homeDeliveryRecipientInfo = null;
        StorePickupRecipientInfo storePickupRecipientInfo = null;
        if ("SF_EXPRESS".equals(shippingMethod.getCode())) {
            homeDeliveryRecipientInfo = HomeDeliveryRecipientInfo
                    .builder()
                    .city(order.getRecipientCity())
                    .zipCode(order.getZipCode())
                    .name(order.getRecipientName())
                    .phone(order.getRecipientPhone())
                    .address(order.getRecipientAddress())
                    .build();
        } else {
            storePickupRecipientInfo = StorePickupRecipientInfo
                    .builder()
                    .recipientName(order.getRecipientName())
                    .phone(order.getRecipientPhone())
                    .storeId(order.getStoreId())
                    .storeName(order.getStoreName())
                    .storeAddress(order.getRecipientAddress())
                    .build();
        }
        // 取得訂單明細
        Map<Integer, Product> productMap = new HashMap<>();
        List<OrderItem> orderItems = getOrderItemsByOrderId(orderId);
        List<DeliveryNoteItemVO> itemVOList = orderItems.stream().map(item -> {
            Integer productId = item.getProductId();
            Product p = null;
            if (productMap.containsKey(productId)) {
                p = productMap.get(productId);
            } else {
                p = productMapper.selectByPrimaryKey(productId);
                productMap.put(productId, p);
            }

            return DeliveryNoteItemVO.builder()
                    .productId(productId)
                    .productName(item.getProductName())
                    .productImage(buildMainImageUrl(p))
                    .unitPrice(item.getUnitPrice())
                    .quantity(item.getQuantity())
                    .subtotal(item.getSubtotal())
                    .build();
            }).collect(Collectors.toList());


        return DeliveryNoteVO.builder()
                .memberEmail(user.getEmail())
                .shippingMethodCode(shippingMethod.getCode())
                .shippingMethodName(shippingMethod.getName())
                .homeDeliveryRecipient(homeDeliveryRecipientInfo)
                .storePickupRecipient(storePickupRecipientInfo)
                .trackingNo(getTrackingNoByOrder(orderId))
                .orderId(order.getId())
                .orderDate(order.getCreateTime())
                .totalAmount(order.getTotalAmount())
                .shippingAmount(BigDecimal.valueOf(shippingMethod.getFee()))
                .remark(order.getRemark())
                .items(itemVOList)
                .build();
    }

    private String getTrackingNoByOrder(String orderId) {
        ShippingTrackingExample ste = new ShippingTrackingExample();
        ste.createCriteria().andOrderIdEqualTo(orderId);
        List<ShippingTracking> shippingTrackings = shippingTrackingMapper.selectByExample(ste);
        ShippingTracking target = shippingTrackings != null && shippingTrackings.size() > 0 ?
                shippingTrackings.get(0) : null;
        String trackingNo = target != null ?
                StringUtils.defaultIfBlank(target.getWaybillNo(), target.getVendororder()) : null;
        return trackingNo;
    }
}

