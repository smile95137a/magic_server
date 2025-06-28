package com.qiyuan.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ProductInfo {
    private Integer id;

    @NotBlank
    private String categoryId;

    @NotBlank
    private String name;

    private String subtitle;

    private String description;

    private String remark;

    @NotNull
    private BigDecimal originalPrice;

    @NotNull
    private BigDecimal specialPrice;

    @NotBlank
    private String mainImageBase64;

    private List<String> imagesBase64;

    private List<String> descriptionBase64;

    @NotNull
    private Boolean status;

    private Date createTime;

    private Date updateTime;
}
