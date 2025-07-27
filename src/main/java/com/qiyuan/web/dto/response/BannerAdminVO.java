package com.qiyuan.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class BannerAdminVO {
    private Integer id;

    private String imageName;

    private String imageBase64;

    private String type;

    private Short sort;

    private Date availableFrom;

    private Date availableUntil;

    private String description;

    private String url;
}
