package com.qiyuan.web.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Oauth2LoginRequest {
    @Schema(description = "第三方平台，如 google、facebook", example = "google", required = true)
    private String provider;

    @Schema(description = "第三方平台授權後的唯一ID", example = "1234567890", required = true)
    private String oauthId;

    @Schema(description = "使用者 Email", example = "user@example.com", required = true)
    private String email;

    @Schema(description = "使用者暱稱", example = "小明", required = false)
    private String nickname;
}

