package com.qiyuan.web.service;

import com.qiyuan.security.exception.ApiException;
import com.qiyuan.web.dao.*;
import com.qiyuan.web.dto.CreateOrderItem;
import com.qiyuan.web.dto.OrderItemVO;
import com.qiyuan.web.dto.request.CancelOrderRequest;
import com.qiyuan.web.dto.request.CreateOrderRequest;
import com.qiyuan.web.dto.request.QueryOrderRequest;
import com.qiyuan.web.dto.response.*;
import com.qiyuan.web.entity.*;
import com.qiyuan.web.entity.example.OrderItemExample;
import com.qiyuan.web.entity.example.OrdersExample;
import com.qiyuan.web.entity.example.ShippingMethodExample;
import com.qiyuan.web.enums.InvoiceType;
import com.qiyuan.web.enums.OrderStatus;
import com.qiyuan.web.enums.PaymentEnum;
import com.qiyuan.web.util.DateUtil;
import com.qiyuan.web.util.RandomGenerator;
import com.qiyuan.web.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrdersMapper ordersMapper;
    private final OrderItemMapper orderItemMapper;
    private final ShippingMethodMapper shippingMethodMapper;
    private final UserMapper userMapper;
    private final ProductMapper productMapper;

    public CreateOrderResponse createOrder(CreateOrderRequest request) {
        String currentUsername = SecurityUtils.getCurrentUsername();
        User user = userMapper.selectByUsername(currentUsername);

        Date currentDate = DateUtil.getCurrentDate();
        Orders order = new Orders();
        order.setId(RandomGenerator.getUUID());
        order.setUserId(user.getId());
        order.setTotalAmount(BigDecimal.ZERO); // 等下計算
        order.setPaid(false);
        order.setStatus(OrderStatus.CREATED.getValue());
        order.setShippingMethodId(request.getShippingMethodId());
        InvoiceType type = InvoiceType.fromValue(request.getInvoiceType());
        order.setInvoiceType(type.getValue());
        order.setInvoiceTarget(request.getInvoiceTarget());
        order.setRecipientName(user.getAddressName());
        order.setRecipientPhone(user.getPhone());
        order.setRecipientAddress(user.getAddress());
        order.setRemark(request.getRemark());
        order.setCreateTime(currentDate);
        order.setUpdateTime(currentDate);
        ordersMapper.insertSelective(order);

        BigDecimal total = BigDecimal.ZERO;
        for (CreateOrderItem item : request.getItems()) {
            Product product = productMapper.selectByPrimaryKey(item.getProductId());
            BigDecimal unitPrice = product.getSpecialPrice();
            BigDecimal subtotal = unitPrice.multiply(BigDecimal.valueOf(item.getQuantity()));
            total = total.add(subtotal);

            OrderItem detail = new OrderItem();
            detail.setOrderId(order.getId());
            detail.setProductId(item.getProductId());
            detail.setProductName(product.getName());
            detail.setUnitPrice(unitPrice);
            detail.setQuantity(item.getQuantity());
            detail.setSubtotal(subtotal);
            detail.setCreateTime(currentDate);
            orderItemMapper.insertSelective(detail);
        }

        Orders update = new Orders();
        update.setId(order.getId());
        update.setTotalAmount(total);
        update.setUpdateTime(new Date());
        ordersMapper.updateByPrimaryKeySelective(update);

        CreateOrderResponse resp = new CreateOrderResponse();
        resp.setOrderId(order.getId());
        resp.setTotalAmount(total);
        return resp;
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
            criteria.andCreateTimeLessThanOrEqualTo(request.getEndTime());
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

    public void cancelOrder(CancelOrderRequest request) {
        String currentUsername = SecurityUtils.getCurrentUsername();
        User user = userMapper.selectByUsername(currentUsername);

        Orders order = ordersMapper.selectByPrimaryKey(request.getOrderId());
        if (order == null || !order.getUserId().equals(user.getId())) {
            throw new RuntimeException("訂單不存在或無權限操作");
        }
        if (!"created".equals(order.getStatus())) {
            throw new RuntimeException("僅能取消未付款/未出貨訂單");
        }
        Orders update = new Orders();
        update.setId(order.getId());
        update.setStatus("cancelled");
        update.setRemark(request.getRemark());
        update.setUpdateTime(new Date());
        ordersMapper.updateByPrimaryKeySelective(update);
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
        vo.setStatus(OrderStatus.fromValue(o.getStatus().toLowerCase(Locale.ROOT)).getLabel());
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
        vo.setStatus(OrderStatus.fromValue(o.getStatus().toLowerCase(Locale.ROOT)).getLabel());
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

    public void markAsPaid(String orderId) {
        Orders order = ordersMapper.selectByPrimaryKey(orderId);
        if (order == null) {
            throw new ApiException("找不到訂單");
        }
        Orders update = new Orders();
        update.setId(orderId);
        update.setPaid(true);
        update.setUpdateTime(new Date());
        ordersMapper.updateByPrimaryKeySelective(update);
    }


}
