package com.qiyuan.web.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ProductCategoryVO {
    @Schema(description = "分類ID，32碼小寫16進位UUID", example = "b9b0a5e7afc242f093b0c174ffbfa00e")
    private String id;

    @Schema(description = "分類名稱", example = "美食")
    private String name;

    @Schema(description = "分類描述", example = "與美食相關的分類")
    private String description;
}
