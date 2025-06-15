package com.qiyuan.web.entity;

import java.util.Date;

public class LanternPurchase {
    private Integer id;

    private String lanternId;

    private String userId;

    private String name;

    private Date birthday;

    private String message;

    private Date createTime;

    private Date expiredTime;

    private Short blessingTimes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLanternId() {
        return lanternId;
    }

    public void setLanternId(String lanternId) {
        this.lanternId = lanternId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Date expiredTime) {
        this.expiredTime = expiredTime;
    }

    public Short getBlessingTimes() {
        return blessingTimes;
    }

    public void setBlessingTimes(Short blessingTimes) {
        this.blessingTimes = blessingTimes;
    }
}