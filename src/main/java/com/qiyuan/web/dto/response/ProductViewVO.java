package com.qiyuan.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductViewVO {
    private Integer id;
    private String categoryId;
    private String name;
    private BigDecimal originalPrice;
    private BigDecimal specialPrice;
    private String mainImageUrl;
}
