package com.qiyuan.web.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ProductVO {
    @Schema(description = "商品ID", example = "101")
    private Integer id;

    @Schema(description = "分類ID，32碼小寫16進位UUID", example = "b9b0a5e7afc242f093b0c174ffbfa00e")
    private String categoryId;

    @Schema(description = "商品名稱", example = "彩虹蛋糕")
    private String name;

    @Schema(description = "商品副標題", example = "七彩繽紛的生日蛋糕")
    private String subtitle;

    @Schema(description = "商品描述", example = "使用天然色素製成的健康彩虹蛋糕")
    private String description;

    @Schema(description = "備註說明", example = "限量販售")
    private String remark;

    @Schema(description = "原價", example = "599.00")
    private BigDecimal originalPrice;

    @Schema(description = "特價", example = "499.00")
    private BigDecimal specialPrice;

    @Schema(description = "商品主圖Base64編碼", example = "data:image/jpeg;base64,...")
    private String mainImageBase64;
}
