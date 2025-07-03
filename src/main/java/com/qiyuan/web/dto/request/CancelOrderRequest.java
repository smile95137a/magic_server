package com.qiyuan.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CancelOrderRequest {
    @NotBlank
    private String orderId;
    private String remark; // 可選，取消原因
}
