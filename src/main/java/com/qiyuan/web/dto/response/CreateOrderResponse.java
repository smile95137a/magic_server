package com.qiyuan.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CreateOrderResponse {
    private String orderId;
    private String externalOrderNo;
    private BigDecimal totalAmount;

    private String status;           // 訂單狀態
    private String paymentStatus;    // 付款狀態
    private String payMethod;        // 金流方式
    private String shippingMethod;   // 物流方式
    private String paymentUrl;       // 金流付款頁連結
    private String paymentInfo;      // 金流附加資訊（ex: Qrcode, 超商代碼等）
    private String createTime;       // 下單時間(格式建議 yyyy-MM-dd HH:mm:ss)
}

