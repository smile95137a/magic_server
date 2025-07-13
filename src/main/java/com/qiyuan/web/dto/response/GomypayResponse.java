package com.qiyuan.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GomypayResponse {
    private String code;       // 狀態碼
    private String msg;        // 訊息
    private String tradeNo;    // 交易編號
    private String paymentUrl; // 付款網址

}
