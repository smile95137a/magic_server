package com.qiyuan.web.entity;

import java.util.Date;

public class GodPurchase {
    private String id;

    private String userId;

    private String godId;

    private Byte durationDays;

    private String status;

    private String externalOrderNo;

    private Date createTime;

    private Date updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGodId() {
        return godId;
    }

    public void setGodId(String godId) {
        this.godId = godId;
    }

    public Byte getDurationDays() {
        return durationDays;
    }

    public void setDurationDays(Byte durationDays) {
        this.durationDays = durationDays;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExternalOrderNo() {
        return externalOrderNo;
    }

    public void setExternalOrderNo(String externalOrderNo) {
        this.externalOrderNo = externalOrderNo;
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