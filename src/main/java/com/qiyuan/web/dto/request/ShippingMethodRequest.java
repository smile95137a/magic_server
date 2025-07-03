package com.qiyuan.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ShippingMethodRequest {
    @NotBlank
    private String id;
    @NotBlank
    private String name;
    private String description;
    @NotNull
    private Integer fee;
    @NotNull
    private Boolean status;
    private Short sort;
}
