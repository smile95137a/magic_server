package com.qiyuan.web.entity;

import lombok.Builder;

import java.util.Date;

@Builder
public class GodInfo {
    private String userId;

    private String godId;

    private Byte level;

    private Byte exp;

    private Date jiaobeiLastTime;

    private Date goldenExpiration;

    private Date onshelfTime;

    private Date offshelfTime;

    private Date cooldownTime;

    private String offeringList;

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

    public Byte getLevel() {
        return level;
    }

    public void setLevel(Byte level) {
        this.level = level;
    }

    public Byte getExp() {
        return exp;
    }

    public void setExp(Byte exp) {
        this.exp = exp;
    }

    public Date getJiaobeiLastTime() {
        return jiaobeiLastTime;
    }

    public void setJiaobeiLastTime(Date jiaobeiLastTime) {
        this.jiaobeiLastTime = jiaobeiLastTime;
    }

    public Date getGoldenExpiration() {
        return goldenExpiration;
    }

    public void setGoldenExpiration(Date goldenExpiration) {
        this.goldenExpiration = goldenExpiration;
    }

    public Date getOnshelfTime() {
        return onshelfTime;
    }

    public void setOnshelfTime(Date onshelfTime) {
        this.onshelfTime = onshelfTime;
    }

    public Date getOffshelfTime() {
        return offshelfTime;
    }

    public void setOffshelfTime(Date offshelfTime) {
        this.offshelfTime = offshelfTime;
    }

    public Date getCooldownTime() {
        return cooldownTime;
    }

    public void setCooldownTime(Date cooldownTime) {
        this.cooldownTime = cooldownTime;
    }

    public String getOfferingList() {
        return offeringList;
    }

    public void setOfferingList(String offeringList) {
        this.offeringList = offeringList;
    }
}