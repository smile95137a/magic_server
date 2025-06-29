package com.qiyuan.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class GalleryImageVO {
    private String url;
    private Integer sort;
    private Long id;
    private String description;
}
