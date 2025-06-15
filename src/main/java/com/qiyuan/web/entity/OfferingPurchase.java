package com.qiyuan.web.entity;

import java.util.Date;

public class OfferingPurchase {
    private Integer id;

    private String offeringId;

    private String userId;

    private String godId;

    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOfferingId() {
        return offeringId;
    }

    public void setOfferingId(String offeringId) {
        this.offeringId = offeringId;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}