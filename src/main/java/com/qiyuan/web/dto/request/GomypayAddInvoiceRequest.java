package com.qiyuan.web.dto.request;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GomypayAddInvoiceRequest {
    private String userId;           // 商家統編（userId，8碼）
    private String checkPwd;         // API 密碼
    private String orderId;          // 對應訂單編號
    private String buyerIdentifier;  // 買方統一編號
    private String buyerName;        // 買方名稱
    private String carrierType;      // 載具類型
    private String carrierId1;       // 載具內容
    private String npoban;           // 捐贈碼
    private String printMark;        // 是否列印 N/Y
    private BigDecimal amount;       // 總金額
    private BigDecimal tax;          // 稅額
    private List<InvoiceItem> items; // 明細
    private String remark;           // 備註
}
