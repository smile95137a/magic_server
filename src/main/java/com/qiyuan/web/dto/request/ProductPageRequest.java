package com.qiyuan.web.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ProductPageRequest {
    @Schema(description = "第幾頁（從1開始）", example = "1")
    private int page = 1;

    @Schema(description = "每頁幾筆", example = "10")
    private int size = 10;

    private String categoryId;
}
