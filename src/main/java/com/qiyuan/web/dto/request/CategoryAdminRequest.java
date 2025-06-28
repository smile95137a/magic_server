package com.qiyuan.web.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CategoryAdminRequest {

    @Schema(description = "分類名稱", example = "精油")
    @NotBlank
    private String name;
    @Schema(description = "分類說明", example = "關於精油類型的描述")
    private String description;
}
