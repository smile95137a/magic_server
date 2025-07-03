package com.qiyuan.web.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CreateOrderItem {
    @NotNull
    private Integer productId;

    @NotNull
    private Integer quantity;
}