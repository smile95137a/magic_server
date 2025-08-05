package com.qiyuan.web.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class StorePickupRequest {
    /** 客戶訂單編號 */
    private String orderId;

    /** 門市代號 */
    private String storeId;

    /** 實際收取金額 */
    private BigDecimal amount;

    /** 商品價值 */
    private BigDecimal orderAmount;

    /** 寄件人姓名 */
    private String senderName;

    /** 寄件人手機 */
    private String sendMobilePhone;

    /** 收件人姓名 */
    private String receiverName;

    /** 收件人手機 */
    private String receiverMobilePhone;

    /** 通路代碼 (如：1=全家、2=萊爾富、3=統一超商...) */
    private String opMode;

    /** 出貨日期 (yyyy/MM/dd 格式) */
    private Date shipDate;
}

