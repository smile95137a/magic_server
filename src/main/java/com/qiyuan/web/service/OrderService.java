package com.qiyuan.web.service;

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
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final PaymentService paymentService;

    @Transactional
    public CreateOrderResponse createOrder(CreateOrderRequest request) {
        // 1. 取得登入會員
        String currentUsername = SecurityUtils.getCurrentUsername();
        User user = userMapper.selectByUsername(currentUsername);
        if (user == null) throw new ApiException("會員不存在");

        Date currentDate = DateUtil.getCurrentDate();
        List<CreateOrderItem> items = request.getItems();
        if (items == null || items.isEmpty())
            throw new ApiException("請選擇購買商品");

        String orderId = RandomGenerator.getUUID();

        BigDecimal total = BigDecimal.ZERO;
        // 2. 處理每個商品（驗證 + 扣庫存 + 建明細）
        for (CreateOrderItem item : items) {
            Product product = productMapper.selectByPrimaryKey(item.getProductId());
            if (product == null || !Boolean.TRUE.equals(product.getStatus()))
                throw new ApiException("商品不存在或已下架");

            int needQty = item.getQuantity() == null ? 1 : item.getQuantity();
            if (product.getStock() == null || product.getStock() < needQty)
                throw new ApiException("[" + product.getName() + "]庫存不足");

            // 扣庫存
            productMapper.decreaseStock(product.getId(), needQty); // 請確定你有實作這個方法（UPDATE product set stock=stock-#{needQty} where id=#{id} and stock>=#{needQty}）

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
        // TODO: 不確定，待確定
        String recipientName = null, recipientPhone = null, recipientAddress = null;
        ShippingMethod shippingMethod = shippingMethodMapper.selectByPrimaryKey(request.getShippingMethodId());
        if (StringUtils.equals("SF_EXPRESS", shippingMethod.getCode())) {
            HomeDeliveryRecipientInfo r = request.getHomeDeliveryRecipient();
            if (r == null) throw new ApiException("宅配需填寫收件人資訊");
            recipientName = r.getName();
            recipientPhone = r.getPhone();
            recipientAddress = r.getAddress();
        } else {
            StorePickupRecipientInfo r = request.getStorePickupRecipient();
            if (r == null) throw new ApiException("超商取貨需填寫收件人資訊");
            recipientName = r.getRecipientName();
            recipientPhone = r.getPhone();
            recipientAddress = r.getStoreAddress();
        }

        // 4. 建立訂單主表
        Orders order = Orders.builder()
                .id(orderId)
                .externalOrderNo(orderId)
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
                .build();
        ordersMapper.insertSelective(order);

        // 5. 金流處理
//        PayMethodEnum payMethod = PayMethodEnum.fromCode(request.getPayMethod());
//        PaymentCreateResult paymentResult = paymentService.createPayment(
//                user, externalOrderNo, total, payMethod, SourceTypeEnum.REAL, orderId
//        );

        return CreateOrderResponse.builder()
                .orderId(orderId)
                .externalOrderNo(orderId)
                .totalAmount(total)
                .status(OrderStatus.CREATED.getValue())
                .paymentStatus("pending")
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
