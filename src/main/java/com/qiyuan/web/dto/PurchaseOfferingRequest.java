package com.qiyuan.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseOfferingRequest {
    @NotBlank
    private String godCode;

    @NotBlank
    private String newOfferingId;

    private String prevOfferingId;
}
