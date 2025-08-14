package com.qiyuan.web.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
public class IssueInvoiceResponse {

    /**
     * 狀態：1 表示成功，0 表示失敗
     */
    @JsonProperty("result")
    private String result;

    /**
     * 系統回傳訊息，例如錯誤原因或成功描述
     */
    @JsonProperty("message")
    private String message;

    /**
     * 發票號碼（10碼）
     */
    @JsonProperty("InvoiceNumber")
    private String invoiceNumber;

    /**
     * 發票日期（格式：yyyy/MM/dd）
     */
    @JsonProperty("InvoiceDate")
    private String invoiceDate;

    /**
     * 發票時間（格式：HH:mm:ss）
     */
    @JsonProperty("InvoiceTime")
    private String invoiceTime;

    /**
     * 隨機碼（4碼）
     */
    @JsonProperty("RandomNumber")
    private String randomNumber;

    /**
     * 稅額（整數）
     */
    @JsonProperty("TaxAmount")
    private String taxAmount;

    /**
     * 總金額（整數）
     */
    @JsonProperty("TotalAmount")
    private String totalAmount;

    /**
     * 商品名稱，多項用半形逗號分隔
     */
    @JsonProperty("Description")
    private String description;

    /**
     * 商品數量，多項用半形逗號分隔
     */
    @JsonProperty("Quantity")
    private String quantity;

    /**
     * 商品單價，多項用半形逗號分隔
     */
    @JsonProperty("UnitPrice")
    private String unitPrice;

    /**
     * 商品金額（各品項小計），多項用半形逗號分隔
     */
    @JsonProperty("Amount")
    private String amount;

    /**
     * 商家 AES 金鑰（可能用於加密處理）
     */
    @JsonProperty("AES")
    private String aes;

    /**
     * 開立方式（可能為手動、自動、API等）
     */
    @JsonProperty("AdditionalInformationType")
    private String additionalInformationType;
}
