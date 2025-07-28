package com.qiyuan.web.dto.response;

import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Builder
public class PaymentNoVO {
    private String externalPaymentNo;
    private BigDecimal price;
}
