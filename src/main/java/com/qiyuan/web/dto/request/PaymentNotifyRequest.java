package com.qiyuan.web.dto.request;

import com.qiyuan.web.util.JsonUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentNotifyRequest {
    private String orderNo;    // 訂單編號
    private String tradeNo;    // 金流平台交易編號
    private String status;     // 狀態（可能是"paid"、"fail"、"pending"等）
    private String amount;     // 交易金額
    private String strCheck;   // 驗證密碼（Str_Check）

    public String toJson() {
        try {
            return JsonUtil.toJson(this);
        } catch (Exception e) {
            return "";
        }
    }
}
