package com.qiyuan.web.dto.request;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GomypayVoidInvoiceRequest {
    private String userId;         // 商家統編
    private String checkPwd;       // API 密碼
    private String invoiceNumber;  // 發票號碼
    private String invoiceDate;    // 發票開立日期（yyyy/MM/dd 或 yyyyMMdd）
    private String reason;         // 作廢原因
}
