package com.qiyuan.web.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ExpressDeliveryRequest {
    /** 客戶訂單編號 */
    private String orderId;

    /** 商品價值 */
    private BigDecimal declaredValue;

    /** 商品總重量（公斤） */
    private BigDecimal totalWeight;

    /** 寄件方式（0=服務點自寄, 1=上門收件） */
    private Integer pickupType;

    /** 上門收件時間（若 pickupType = 1 時必填，格式: yyyy-MM-dd HH:mm） */
    private Date pickupAppointTime;

    /** 商品名稱 */
    private String productName;

    /** 商品數量 */
    private Integer quantity;

    /** 收件人資訊 */
    private String recipientName;
    private String recipientMobile;
    private String recipientRegion;    // 縣市
    private String recipientAddress;   // 詳細地址（不含縣市）
    private String recipientZipCode;

    /** 寄件人資訊 */
    private String senderName;
    private String senderMobile;
    private String senderRegion;       // 縣市
    private String senderAddress;      // 詳細地址（不含縣市）
    private String senderZipCode;

    /** 訂單備註 */
    private String remark;

    /** 檢查碼 */
    private String chkMac;

}
