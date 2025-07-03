package com.qiyuan.web.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class OrderVO {
    private String id;
    private BigDecimal totalAmount;
    private String status;
    private String paymentStatus;
    private String shippingMethod;
    private String trackingNo;
    private Date createTime;
    private Date updateTime;
}

