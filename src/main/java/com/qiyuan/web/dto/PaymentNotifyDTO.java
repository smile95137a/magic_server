package com.qiyuan.web.dto;
import lombok.Data;

@Data
public class PaymentNotifyDTO {
    /** 固定 0:信用卡/1:銀聯/2:超商/4:ATM/3:WebAtm/6:超商代碼/7:LinePay */
    private String Send_Type;
    /** 結果 0=失敗, 1=成功 */
    private String result;
    /** 回傳訊息 */
    private String ret_msg;
    /** GOMYPAY 系統訂單編號 */
    private String OrderID;
    /** 客戶自訂單號(你的 externalOrderNo) */
    private String e_orderno;
    /** 商店代號 */
    private String e_no;
    /** 交易金額 */
    private String e_money;
    /** 幣別 */
    private String e_Cur;
    /** 交易日期 yyyymmdd */
    private String e_date;
    /** 交易時間 HH:mm:ss */
    private String e_time;
    /** 授權碼 */
    private String avcode;
    /** 交易手續費 */
    private String e_outlay;
    /** 驗證用 md5 碼 */
    private String str_check;
    /** 發票號碼（如有） */
    private String Invoice_No;
    /** 信用卡後四碼（如有） */
    private String CardLastNum;
    /** 超商/ATM繳費帳號（如有） */
    private String e_payaccount;
    /** 超商條碼1（如有） */
    private String code1;
    /** 超商條碼2（如有） */
    private String code2;
    /** 超商條碼3（如有） */
    private String code3;
    /** 繳費期限（如有） */
    private String LimitDate;
    /** 實際繳費金額（如有） */
    private String PayAmount;
    // 你可視需要加其他欄位
}
