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
public class ShippingMethodUpdateRequest {
    @NotBlank
    private String id;

    @NotBlank
    private String name;

    private String description;

    private Integer fee;

    private Boolean status;

    private Integer sort;

    private Integer minSize;

    private Integer maxSize;
}
