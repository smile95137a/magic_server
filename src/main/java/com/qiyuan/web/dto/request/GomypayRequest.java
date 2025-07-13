package com.qiyuan.web.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GomypayRequest {
    private String sendType;
    private String orderNo;
    private BigDecimal amount;
    private String userName;
    private String phone;
    private String email;
    private String memo;
    private String transMode;
    private String installment;
}
