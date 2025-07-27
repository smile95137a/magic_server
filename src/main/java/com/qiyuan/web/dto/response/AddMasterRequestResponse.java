package com.qiyuan.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AddMasterRequestResponse {
    private String orderNo;
    private String externalPaymentNo;
}
