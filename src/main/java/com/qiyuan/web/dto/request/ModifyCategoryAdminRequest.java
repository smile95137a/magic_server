package com.qiyuan.web.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ModifyCategoryAdminRequest {
    @Schema(description = "分類ID，32碼小寫16進位UUID（不含-）", example = "b9b0a5e7afc242f093b0c174ffbfa00e")
    @NotBlank
    @Pattern(regexp = "^[a-f0-9]{32}$", message = "ID必須為32碼小寫16進位UUID")
    private String id;

    @Schema(description = "分類名稱", example = "美食")
    private String name;

    @Schema(description = "分類描述", example = "與美食相關的分類")
    private String description;

    @Schema(description = "啟用狀態", example = "true")
    private Boolean status;
}
