package com.qiyuan.web.dto.request;

import com.qiyuan.web.dto.GalleryImageVO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class EditProductRequest {
    @NotNull
    private Integer productId;
    @NotBlank
    private String name;
    private String categoryId;
    private String subtitle;
    private String description;
    private String remark;
    private BigDecimal originalPrice;
    private BigDecimal specialPrice;
    private String mainImage;
    private List<GalleryImageVO> galleryImages;
    private String detailHtml;
    private Boolean status;
}