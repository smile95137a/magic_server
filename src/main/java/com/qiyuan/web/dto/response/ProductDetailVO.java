package com.qiyuan.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDetailVO {
    private Integer id;
    private String categoryId;
    private String name;
    private String subtitle;
    private String description;     // 商品短描述
    private String remark;          // 備註
    private BigDecimal originalPrice;
    private BigDecimal specialPrice;
    private String mainImageUrl;    // 主圖完整URL
    private List<String> galleryImageUrls;
    private String detailHtml;      // 富文字描述，含內嵌圖
    private Boolean status;
}
