package com.qiyuan.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LanternBlessingDTO {
    private String id;

    private String lanternCode;

    private String userId;

    private String name;

    private Date birthday;

    private String message;

    private Date createTime;

    private Date expiredTime;

    private Short blessingTimes;
}
