package com.qiyuan.web.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentTransaction {
    private String id; // 同 externalOrderNo

    private String userId;

    private String sourceType;

    private BigDecimal amount;

    private String status;

    private String payMethod;

    private String rawData;

    private Date createTime;

    private Date updateTime;

    private String providerOrderNo;

}