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
public class PresentOfferingRequest {
    @Schema(description = "神明代號，g-開頭，後面接1~4碼英文字母", example = "g-gg")
    @NotBlank
    @Pattern(regexp = "^g-[a-z]{1,4}$", message = "代號錯誤")
    private String godCode;

    @Schema(description = "前一個供品ID，32碼小寫16進位（UUID無'-'），可以null值", example = "b9b0a5e7afc242f093b0c174ffbfa00e")
    @Pattern(regexp = "^[a-fA-F0-9]{32}$", message = "代號錯誤")
    private String prevOfferingId;

    @Schema(description = "新的供品ID，32碼小寫16進位（UUID無'-'），不可空值", example = "b9b0a5e7afc242f093b0c174ffbfa00e")
    @NotBlank
    @Pattern(regexp = "^[a-fA-F0-9]{32}$", message = "代號錯誤")
    private String newOfferingId;
}
