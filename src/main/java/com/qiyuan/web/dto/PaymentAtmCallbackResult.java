package com.qiyuan.web.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@ToString
public class PaymentAtmCallbackResult {
    private String sendType;
    private String result;
    private String retMsg;
    private String orderID;
    private String eMoney;
    private String payAmount;
    private String eDate;
    private String eTime;
    private String eOrderno;
    private String ePayaccount;
    private String ePayInfo;
    private String strCheck;
}
