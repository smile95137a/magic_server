package com.qiyuan.web.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
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
    private String imageBase64;
    @Schema(description = "banner說明", example = "愛像一道暢行無阻的綠燈")
    private String description;
}
