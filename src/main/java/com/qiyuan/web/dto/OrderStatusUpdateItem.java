package com.qiyuan.web.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class OrderStatusUpdateItem {
    @NotBlank
    private String orderId;
    private String trackingNo;
    private String remark;
}