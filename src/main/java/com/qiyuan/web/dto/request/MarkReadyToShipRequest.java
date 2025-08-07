package com.qiyuan.web.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class MarkReadyToShipRequest {
    @NotNull
    private String orderId;
    private String remark;
    @NotNull
    private Date shippingDate;

}
