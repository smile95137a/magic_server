package com.qiyuan.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ProductAdminVO {
    private Integer id;
    private String categoryId;
    private String name;
    private String subtitle;
    private String description;
    private String remark;
    private BigDecimal originalPrice;
    private BigDecimal specialPrice;
    private String mainImageUrl;
    private List<GalleryImageVO> galleryImages;
    private String detailHtml;
    private Boolean status;
}

