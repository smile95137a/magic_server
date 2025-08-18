package com.qiyuan.web.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LanternPurchase {
    private String id;

    private String externalOrderNo;

    private String lanternId;

    private String userId;

    private String name;

    private Date birthday;

    private String message;

    private Short blessingTimes;

    private Date createTime;

    private Date expiredTime;
    
    private Boolean checkedIn;

}