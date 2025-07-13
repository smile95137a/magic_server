package com.qiyuan.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentCreateResult {
    private String type;          // 支付方式(可選填)
    private String paymentUrl;    // 付款頁網址
    private String qrCodeUrl;     // QRCode 圖片連結(如果有)
    private String payCode;       // 超商代碼、繳費代碼等(如果有)
    private String expireTime;    // 繳費期限(如果有)
    private String rawData;       // API 原始回傳資料(JSON字串，可選)
}
