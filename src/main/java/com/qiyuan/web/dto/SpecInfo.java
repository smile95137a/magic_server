package com.qiyuan.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SpecInfo {
    private Integer specId;
    private String specValue;
    private Integer stock;
}
