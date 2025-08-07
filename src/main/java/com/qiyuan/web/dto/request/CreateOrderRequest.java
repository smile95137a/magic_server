package com.qiyuan.web.dto.request;

import com.qiyuan.web.dto.CreateOrderItem;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOrderRequest {

    @Schema(description = "購買商品清單", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private List<CreateOrderItem> items;

    @Schema(description = "物流方式ID", example = "blackcat", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String shippingMethodId;

    @Schema(description = "付款方式", example = "credit_card", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String payMethod;  // 支援多金流

    @Schema(description = "發票類型", example = "personal", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String invoiceType;

    @Schema(description = "發票開立對象(個人或公司/統編)", example = "12345678")
    private String invoiceTarget;

    @Schema(description = "載具類型", example = "1" /* 1:手機條碼, 2:自然人憑證, 3:會員載具, 9:無載具 */, requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String carrierType;

    @Schema(description = "載具內容", example = "/ABCD1234" /* 手機條碼、自然人憑證等 */)
    private String carrierId;

    @Schema(description = "捐贈碼", example = "16888")
    private String npoban;

    @Schema(description = "訂單備註", example = "請儘速出貨")
    private String remark;

    @Schema(description = "宅配收件資訊 (當物流方式為宅配時帶此欄)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @Valid
    private HomeDeliveryRecipientInfo homeDeliveryRecipient;

    @Schema(description = "超商取貨收件資訊 (當物流方式為超商時帶此欄)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @Valid
    private StorePickupRecipientInfo storePickupRecipient;
}
