package com.qiyuan.web.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "供奉供品請求")
public class PresentOfferingRequest {

    @Schema(description = "神明代號，g-開頭，後面接1~4碼英文字母", example = "g-gg")
    @NotBlank
    @Pattern(regexp = "^g-[a-z]{1,4}$", message = "代號錯誤")
    private String godCode;

    @Schema(description = "付款方式（選填）", example = "credit_card")
    private String paymentMethod;

    @Schema(description = "供品清單（每筆包含要取代的索引與新的供品ID）")
    @Valid
    private List<OfferingPresentRequest> list;
}
