package com.qiyuan.web.dto.request;

import com.qiyuan.web.dto.CreateOrderItem;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CreateOrderRequest {
    @NotNull
    private List<CreateOrderItem> items;

    @NotBlank
    private String shippingMethodId;

    @NotBlank
    private String invoiceType;

    private String invoiceTarget;

    private String remark;
}

