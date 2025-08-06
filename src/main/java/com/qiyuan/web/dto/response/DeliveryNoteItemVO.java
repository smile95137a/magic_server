package com.qiyuan.web.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Schema(description = "出貨單明細")
public class DeliveryNoteItemVO {
    @Schema(description = "商品ID")
    private Integer productId;
    @Schema(description = "商品主圖")
    private String productImage;
    @Schema(description = "商品名稱")
    private String productName;
    @Schema(description = "單價")
    private BigDecimal unitPrice;
    @Schema(description = "數量")
    private Integer quantity;
    @Schema(description = "小計")
    private BigDecimal subtotal;
}