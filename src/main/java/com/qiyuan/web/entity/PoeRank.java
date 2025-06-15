package com.qiyuan.web.entity;

import java.util.Date;

public class PoeRank {
    private String userId;

    private Short times;

    private Date createTime;

    private Date updateTime;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Short getTimes() {
        return times;
    }

    public void setTimes(Short times) {
        this.times = times;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}