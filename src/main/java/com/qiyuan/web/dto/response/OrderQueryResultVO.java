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
public class OrderQueryResultVO {
    private String id; // 訂單編號
    private String username;
    private BigDecimal totalAmount; //總金額
    private String status; // 訂單狀態
    private String providerOrderNo; // 金流訂單
    private String paymentStatus; // 付款狀態
    private String shippingMethodName; // 物流方式
    private String trackingNo;
    private String logisticsStatus; // 物流訂單狀態
    private String logisticsRecordStatus; // 萬事達物流回傳的狀態碼
    private String invoiceType; // 發票類型
    private String invoiceStatus; // 發票狀態
    private Date createTime;
}
