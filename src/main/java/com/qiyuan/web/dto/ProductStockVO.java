package com.qiyuan.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductStockVO {
    private Integer id;
    private Integer productId;
    private String productName;    // 商品名稱
    private Integer totalStock;
    private Integer reservedStock;
    private Date updateTime;
    private String remark;
    private String url;
}
