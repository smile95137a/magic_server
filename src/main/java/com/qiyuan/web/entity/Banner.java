package com.qiyuan.web.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Banner {
    private Integer id;

    private String imageUrl;

    private String type;

    private Short sort;

    private Date availableFrom;

    private Date availableUntil;
}