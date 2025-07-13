package com.qiyuan.web.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceItem {
    private String itemName;
    private BigDecimal itemPrice;
    private Integer itemCount;
    private BigDecimal itemAmount;
    private String itemRemark;
}
