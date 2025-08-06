package com.qiyuan.web.dto.response;

import com.qiyuan.web.dto.request.HomeDeliveryRecipientInfo;
import com.qiyuan.web.dto.request.StorePickupRecipientInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Schema(description = "出貨單VO")
public class DeliveryNoteVO {
    @Schema(description = "會員Email(帳號)")
    private String memberEmail;

    // 收件人資訊
    @Schema(description = "物流方式")
    private String shippingMethodCode;
    @Schema(description = "物流方式名稱")
    private String shippingMethodName;
    @Schema(description = "收件人宅配收件資訊 (當物流方式為宅配時帶此欄)")
    private HomeDeliveryRecipientInfo homeDeliveryRecipient;
    @Schema(description = "收件人超商取貨收件資訊 (當物流方式為超商時帶此欄)")
    private StorePickupRecipientInfo storePickupRecipient;

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

