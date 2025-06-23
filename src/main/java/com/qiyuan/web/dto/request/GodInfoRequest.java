package com.qiyuan.web.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GodInfoRequest {
    @Schema(description = "神明代號，g-開頭，後面接1~4碼英文字母", example = "g-gg")
    @NotBlank
    @Pattern(regexp = "^g-[a-z]{1,4}$", message = "代號錯誤")
    private String godCode;
}
