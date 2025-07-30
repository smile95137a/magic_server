package com.qiyuan.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PaymentSuccessRequest {

    private String providerOrderNo;

    @NotBlank
    private String externalOrderNo;
    @NotBlank
    @Pattern(regexp = "^[LOMG]$", message = "sourceType 僅允許 L、O、M")
    private String sourceType;
}
