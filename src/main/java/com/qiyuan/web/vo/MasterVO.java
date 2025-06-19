package com.qiyuan.web.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MasterVO {
    private String code;

    private String name;

    private String title;

    private String mainStar;

    private String bio;

    private String experience;

    private String personalItems;

    private List<QaItemVO> serviceItem;

    private String imageBase64;

    private Byte sort;
}
