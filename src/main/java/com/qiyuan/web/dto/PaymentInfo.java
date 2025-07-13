package com.qiyuan.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class PaymentInfo {
    private String paymentUrl;
    private Map<String, String> params;
}
