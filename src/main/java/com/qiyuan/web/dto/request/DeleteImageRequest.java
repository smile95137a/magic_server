package com.qiyuan.web.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class DeleteImageRequest {
    @NotNull
    private Integer productId;
    @NotNull
    private Long imageId; // product_image PK
}
