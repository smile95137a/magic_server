package com.qiyuan.web.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceRequest {
    private String orderId;
    private String buyerIdentifier;
    private String buyerName;
    private String carrierType;
    private String carrierId;
    private String npoban;
    private String printMark;
    private BigDecimal amount;
    private BigDecimal tax;
    private List<InvoiceItem> items;
    private String remark;
}
