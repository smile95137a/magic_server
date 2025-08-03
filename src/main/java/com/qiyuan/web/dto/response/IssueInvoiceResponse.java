package com.qiyuan.web.dto.response;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class IssueInvoiceResponse {

    /**
     * 狀態：1 表示成功，0 表示失敗
     */
    private String result;

    /**
     * 系統回傳訊息，例如錯誤原因或成功描述
     */
    private String message;

    /**
     * 發票號碼（10碼）
     */
    private String InvoiceNumber;

    /**
     * 發票日期（格式：yyyy/MM/dd）
     */
    private String InvoiceDate;

    /**
     * 發票時間（格式：HH:mm:ss）
     */
    private String InvoiceTime;

    /**
     * 隨機碼（4碼）
     */
    private String RandomNumber;

    /**
     * 稅額（整數）
     */
    private String TaxAmount;

    /**
     * 總金額（整數）
     */
    private String TotalAmount;

    /**
     * 商品名稱，多項用半形逗號分隔
     */
    private String Description;

    /**
     * 商品數量，多項用半形逗號分隔
     */
    private String Quantity;

    /**
     * 商品單價，多項用半形逗號分隔
     */
    private String UnitPrice;

    /**
     * 商品金額（各品項小計），多項用半形逗號分隔
     */
    private String Amount;

    /**
     * 商家 AES 金鑰（可能用於加密處理）
     */
    private String AES;

    /**
     * 開立方式（可能為手動、自動、API等）
     */
    private String AdditionalInformationType;
}
