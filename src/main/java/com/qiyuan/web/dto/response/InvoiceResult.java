package com.qiyuan.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceResult {
    private boolean success;
    private String invoiceNumber;
    private String randomNumber;
    private String status;      // issued/failed/voided
    private String message;
    private String rawResponse;
}
