package com.qiyuan.web.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Schema(description = "出貨單VO")
public class DeliveryNoteVO {
    @Schema(description = "會員Email(帳號)")
    private String memberEmail;

    // 收件人資訊
    @Schema(description = "收件人姓名")
    private String recipientName;
    @Schema(description = "收件人電話")
    private String recipientPhone;
    @Schema(description = "物流收件資訊，依物流方式格式變動")
    private Map<String, String> recipientInfo;
    @Schema(description = "物流單號")
    private String trackingNo;

    // 訂單資訊
    @Schema(description = "訂單編號")
    private String orderId;
    @Schema(description = "訂單日期")
    private Date orderDate;
    @Schema(description = "訂單總金額")
    private BigDecimal totalAmount;
    @Schema(description = "運費金額")
    private BigDecimal shippingAmount;
    @Schema(description = "訂單備註")
    private String remark;
    @Schema(description = "訂單明細")
    private List<DeliveryNoteItemVO> items;

}

