package com.qiyuan.web.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Banner {
    private Integer id;

    private String imageUrl;

    private String type;

    private Short sort;

    private Date availableFrom;

    private Date availableUntil;
}