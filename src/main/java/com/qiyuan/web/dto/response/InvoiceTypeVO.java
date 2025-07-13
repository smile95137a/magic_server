package com.qiyuan.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceTypeVO {
    private String code;      // ex: personal, company, donation
    private String label;     // 顯示名稱
}

