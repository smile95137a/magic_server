package com.qiyuan.web.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProductListRequest {
    @Schema(description = "分類ID，不需必填，預設為 ALL", example = "b9b0a5e7afc242f093b0c174ffbfa00e", defaultValue = "ALL")
    private String categoryId;

    @Schema(description = "返回商品筆數，不需必填；最大20筆", example = "10")
    @Max(20)
    private Integer count;
}
