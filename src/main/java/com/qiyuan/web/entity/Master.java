package com.qiyuan.web.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Master {
    private String code;

    private String name;

    private String title;

    private String mainStar;

    private String bio;

    private String experience;

    private String personalItems;

    private String servicesJson;

    private Boolean status;

    private String imageExt;

    private Byte sort;

    private String serviceTime;
}
