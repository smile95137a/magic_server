package com.qiyuan.web.dto;

import com.qiyuan.web.enums.InvoiceType;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@ToString
public class InvoiceDTO {
    private InvoiceType type;
    private String value;
}
