package com.qiyuan.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpressCreateResult {

    /** 客戶訂單編號（即我方的訂單 ID） */
    private String CustomerOrderNo;

    /** 順豐物流訂單編號 */
    private String SfWaybillNo;

    /** 建立訂單成功與否訊息（例：建立訂單成功） */
    private String Msg;

    /** 錯誤訊息（若有錯則這裡會帶內容） */
    private String ErrorMessage;
}
