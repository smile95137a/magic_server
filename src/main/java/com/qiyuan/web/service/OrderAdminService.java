package com.qiyuan.web.service;

import com.qiyuan.security.exception.ApiException;
import com.qiyuan.web.dao.OrderItemMapper;
import com.qiyuan.web.dao.OrdersMapper;
import com.qiyuan.web.dao.ShippingMethodMapper;
import com.qiyuan.web.dao.UserMapper;
import com.qiyuan.web.dto.OrderItemVO;
import com.qiyuan.web.dto.OrderStatusUpdateItem;
import com.qiyuan.web.dto.request.QueryOrderAdminRequest;
import com.qiyuan.web.dto.request.ShippingMethodRequest;
import com.qiyuan.web.dto.request.UpdateOrderStatusBatchRequest;
import com.qiyuan.web.dto.response.*;
import com.qiyuan.web.entity.OrderItem;
import com.qiyuan.web.entity.Orders;
import com.qiyuan.web.entity.ShippingMethod;
import com.qiyuan.web.entity.User;
import com.qiyuan.web.entity.example.OrderItemExample;
import com.qiyuan.web.entity.example.OrdersExample;
import com.qiyuan.web.entity.example.ShippingMethodExample;
import com.qiyuan.web.enums.OrderStatus;
import com.qiyuan.web.enums.PaymentEnum;
import com.qiyuan.web.util.DateUtil;
import com.qiyuan.web.util.RandomGenerator;
import lombok.RequiredArgsConstructor;
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
    private final UserMapper userMapper;
    private final StockService stockService;

    public List<OrderVO> getOrderList(QueryOrderAdminRequest request) {
        OrdersExample example = new OrdersExample();
        OrdersExample.Criteria criteria = example.createCriteria();

        if (request.getUserId() != null && !request.getUserId().isBlank()) {
            criteria.andUserIdEqualTo(request.getUserId());
        }
        if (request.getStatus() != null) {
            criteria.andStatusEqualTo(request.getStatus());
        }
        if (request.getPaymentStatus() != null) {
            criteria.andPaidEqualTo("paid".equalsIgnoreCase(request.getPaymentStatus()));
        }
        if (request.getStartTime() != null) {
            criteria.andCreateTimeGreaterThanOrEqualTo(request.getStartTime());
        }
        if (request.getEndTime() != null) {
            criteria.andCreateTimeLessThanOrEqualTo(request.getEndTime());
        }

        example.setOrderByClause("create_time desc");
        List<Orders> orderList = ordersMapper.selectByExample(example);

        List<OrderVO> voList = new ArrayList<>();
        for (Orders o : orderList) {
            voList.add(toOrderVO(o));
        }
        return voList;
    }

    // 查詢訂單詳情
    public OrderDetailVO getOrderDetail(String orderId) {
        Orders order = ordersMapper.selectByPrimaryKey(orderId);
        if (order == null) {
            throw new ApiException("訂單不存在");
        }
        List<OrderItem> items = getOrderItemsByOrderId(orderId);
        return toOrderDetailVO(order, items);
    }

    // 批次變更訂單狀態
    @Transactional
    public void updateOrderStatusBatch(UpdateOrderStatusBatchRequest request) {
        OrderStatus targetStatus = request.getStatus();

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
                for (OrderItem item : items) {
                    stockService.releaseReservedStock(item.getProductId(), item.getQuantity(), null);
                }
            }

            Orders record = new Orders();
            record.setId(order.getId());
            record.setStatus(targetStatus.getValue());
            if (update.getTrackingNo() != null) {
                record.setTrackingNo(update.getTrackingNo());
            }
            if (update.getRemark() != null) {
                record.setRemark(update.getRemark());
            }
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

    @Transactional
    public DeliveryNoteVO getDeliveryNote(String orderId) {
        Orders order = ordersMapper.selectByPrimaryKey(orderId);
        if (order == null) throw new ApiException("訂單不存在");

        // 取得會員資訊
        User user = userMapper.selectByPrimaryKey(order.getUserId());
        if (user == null) throw new ApiException("查無會員");


        ShippingMethod shippingMethod = shippingMethodMapper.selectByPrimaryKey(order.getShippingMethodId());
        Map<String, String> recipientInfo = new HashMap<>();

        //TODO
//        if ("FAMILY".equalsIgnoreCase(shippingMethod)) {
//            // 假設有這些欄位，請視你的欄位設計填寫
//            recipientInfo.put("storeId", order.getStoreId());
//            recipientInfo.put("storeName", order.getStoreName());
//            recipientInfo.put("storeAddress", order.getStoreAddress());
//            recipientInfo.put("recipientName", order.getRecipientName());
//            recipientInfo.put("recipientPhone", order.getRecipientPhone());
//        } else if ("HOME".equalsIgnoreCase(shippingMethod)) {
//            recipientInfo.put("recipientName", order.getRecipientName());
//            recipientInfo.put("recipientPhone", order.getRecipientPhone());
//            recipientInfo.put("recipientAddress", order.getRecipientAddress());
//        }


        // 取得訂單明細
        List<OrderItem> orderItems = getOrderItemsByOrderId(orderId);
        List<DeliveryNoteItemVO> itemVOList = orderItems.stream().map(item ->
                DeliveryNoteItemVO.builder()
                        .productId(item.getProductId())
                        .productName(item.getProductName())
                        .unitPrice(item.getUnitPrice())
                        .quantity(item.getQuantity())
                        .subtotal(item.getSubtotal())
                        .build()
        ).collect(Collectors.toList());

        return DeliveryNoteVO.builder()
                .memberEmail(user.getEmail())
                .recipientName(order.getRecipientName())
                .recipientPhone(order.getRecipientPhone())
                .recipientInfo(recipientInfo)
                .trackingNo(order.getTrackingNo())
                .orderId(order.getId())
                .orderDate(order.getCreateTime())
                .totalAmount(order.getTotalAmount())
                .shippingAmount(BigDecimal.valueOf(shippingMethod.getFee()))
                .remark(order.getRemark())
                .items(itemVOList)
                .build();
    }
}

