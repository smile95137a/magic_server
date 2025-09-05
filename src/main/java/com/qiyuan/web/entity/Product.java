package com.qiyuan.web.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    private Integer id;

    private String categoryId;

    private String name;

    private String subtitle;

    private String description;

    private String remark;

    private BigDecimal originalPrice;

    private BigDecimal specialPrice;

    private String mainImage;

    private Boolean status;

    private Date createTime;

    private Date updateTime;

    private String detailHtml;

}
