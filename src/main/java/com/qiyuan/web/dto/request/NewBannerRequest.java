package com.qiyuan.web.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class NewBannerRequest extends BannerRequest {

    @Schema(description = "圖片Base64編碼", example = "data:xxx;base64,/9j/4AAQSk...")
    @NotBlank
    private String imageBase64;

}
