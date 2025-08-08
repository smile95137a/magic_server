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

public class ShippingTracking {
    private String id;

    private String orderId;

    private String logisticsType;

    private String logisticsVendor;

    private String waybillNo;

    private String vendororder;

    private String status;

    private String requestPayload;

    private String responsePayload;

    private Date createTime;

    private Date updateTime;

    private String createStatus;

    private String lastCallbackPayload;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getLogisticsType() {
        return logisticsType;
    }

    public void setLogisticsType(String logisticsType) {
        this.logisticsType = logisticsType;
    }

    public String getLogisticsVendor() {
        return logisticsVendor;
    }

    public void setLogisticsVendor(String logisticsVendor) {
        this.logisticsVendor = logisticsVendor;
    }

    public String getWaybillNo() {
        return waybillNo;
    }

    public void setWaybillNo(String waybillNo) {
        this.waybillNo = waybillNo;
    }

    public String getVendororder() {
        return vendororder;
    }

    public void setVendororder(String vendororder) {
        this.vendororder = vendororder;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRequestPayload() {
        return requestPayload;
    }

    public void setRequestPayload(String requestPayload) {
        this.requestPayload = requestPayload;
    }

    public String getResponsePayload() {
        return responsePayload;
    }

    public void setResponsePayload(String responsePayload) {
        this.responsePayload = responsePayload;
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

    public String getCreateStatus() {
        return createStatus;
    }

    public void setCreateStatus(String createStatus) {
        this.createStatus = createStatus;
    }

    public String getLastCallbackPayload() {
        return lastCallbackPayload;
    }

    public void setLastCallbackPayload(String lastCallbackPayload) {
        this.lastCallbackPayload = lastCallbackPayload;
    }
}