package com.qiyuan.web.dto.request;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class PaymentRequest {
    private String payType;
    private String recordId;
    private BigDecimal amount;
    private String payerName;
    private String payerPhone;
    private String payerEmail;
    private String memo;
}
