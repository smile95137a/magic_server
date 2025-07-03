package com.qiyuan.web.service;

import com.qiyuan.security.exception.ApiException;
import com.qiyuan.web.dao.OrderItemMapper;
import com.qiyuan.web.dao.OrdersMapper;
import com.qiyuan.web.dao.ShippingMethodMapper;
import com.qiyuan.web.dto.OrderItemVO;
import com.qiyuan.web.dto.OrderStatusUpdateItem;
import com.qiyuan.web.dto.request.QueryOrderAdminRequest;
import com.qiyuan.web.dto.request.ShippingMethodRequest;
import com.qiyuan.web.dto.request.UpdateOrderStatusBatchRequest;
import com.qiyuan.web.dto.response.OrderDetailVO;
import com.qiyuan.web.dto.response.OrderVO;
import com.qiyuan.web.dto.response.ShippingMethodVO;
import com.qiyuan.web.entity.OrderItem;
import com.qiyuan.web.entity.Orders;
import com.qiyuan.web.entity.ShippingMethod;
import com.qiyuan.web.entity.example.OrderItemExample;
import com.qiyuan.web.entity.example.OrdersExample;
import com.qiyuan.web.entity.example.ShippingMethodExample;
import com.qiyuan.web.util.DateUtil;
import com.qiyuan.web.util.RandomGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderAdminService {

    private final OrdersMapper ordersMapper;
    private final OrderItemMapper orderItemMapper;
    private final ShippingMethodMapper shippingMethodMapper;

    // 查詢所有訂單（可多條件查詢、分頁）
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
    public void updateOrderStatusBatch(UpdateOrderStatusBatchRequest request) {
        for (OrderStatusUpdateItem update : request.getUpdates()) {
            Orders order = ordersMapper.selectByPrimaryKey(update.getOrderId());
            if (order == null) continue;

            Orders record = new Orders();
            record.setId(order.getId());
            record.setStatus(update.getStatus());
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

    // 查詢物流方式列表
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

    // 查詢訂單明細
    public List<OrderItem> getOrderItemsByOrderId(String orderId) {
        OrderItemExample example = new OrderItemExample();
        example.createCriteria().andOrderIdEqualTo(orderId);
        return orderItemMapper.selectByExample(example);
    }

    private OrderVO toOrderVO(Orders o) {
        OrderVO vo = new OrderVO();
        vo.setId(o.getId());
        vo.setTotalAmount(o.getTotalAmount());
        vo.setStatus(o.getStatus());
        vo.setPaymentStatus(o.getPaid() != null && o.getPaid() ? "paid" : "pending");
        vo.setShippingMethod(o.getShippingMethodId());
        vo.setTrackingNo(o.getTrackingNo());
        vo.setCreateTime(o.getCreateTime());
        vo.setUpdateTime(o.getUpdateTime());
        return vo;
    }

    private OrderDetailVO toOrderDetailVO(Orders o, List<OrderItem> items) {
        OrderDetailVO vo = new OrderDetailVO();
        vo.setId(o.getId());
        vo.setTotalAmount(o.getTotalAmount());
        vo.setStatus(o.getStatus());
        vo.setPaymentStatus(o.getPaid() != null && o.getPaid() ? "paid" : "pending");
        vo.setShippingMethod(o.getShippingMethodId());
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
}

