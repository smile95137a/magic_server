package com.qiyuan.web.dto.response;

import com.qiyuan.web.dto.OrderItemVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class OrderDetailVO {
    private String id;
    private BigDecimal totalAmount;
    private String status;
    private String paymentStatus;
    private String invoiceType;
    private String invoiceTarget;
    private String shippingMethod;
    private String recipientName;
    private String recipientPhone;
    private String recipientAddress;
    private String remark;
    private String trackingNo;
    private List<OrderItemVO> items;
    private Date createTime;
    private Date updateTime;
}


