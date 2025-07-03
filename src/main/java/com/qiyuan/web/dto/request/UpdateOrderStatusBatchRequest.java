package com.qiyuan.web.dto.request;

import com.qiyuan.web.dto.OrderStatusUpdateItem;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UpdateOrderStatusBatchRequest {
    @NotEmpty
    private List<OrderStatusUpdateItem> updates;
}