package com.qiyuan.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaySuccessRequest {
    @NotBlank
    private String orderId;
    @NotBlank
    private String externalOrderNo;
    private String providerOrderNo;
}
