package com.qiyuan.web.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "單一供品取代請求")
public class OfferingPresentRequest {

    @Schema(description = "要取代的供品ID的索引號，只允許 0 ~ 2", example = "1")
    @NotNull
    @Min(value = 0, message = "索引最小值為 0")
    @Max(value = 2, message = "索引最大值為 2")
    private Integer index;

    @Schema(description = "新的供品ID，32碼小寫16進位（UUID 無 '-'），不可空值", example = "b9b0a5e7afc242f093b0c174ffbfa00e")
    @NotBlank
    @Pattern(regexp = "^[a-f0-9]{32}$", message = "供品ID格式錯誤")
    private String newOfferingId;
}
