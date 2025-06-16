package com.qiyuan.web.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@AllArgsConstructor
@Builder
@Data
public class BannerVO implements Serializable {
    private String imgBase64;
    private Short sort;

}
