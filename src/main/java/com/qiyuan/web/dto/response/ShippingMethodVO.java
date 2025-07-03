package com.qiyuan.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ShippingMethodVO {
    private String id;
    private String code;
    private String name;
    private String description;
    private Integer fee;
    private Boolean status;
    private Short sort;
    private Date createTime;
    private Date updateTime;
}