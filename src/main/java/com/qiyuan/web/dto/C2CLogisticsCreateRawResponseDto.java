package com.qiyuan.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class C2CLogisticsCreateRawResponseDto {
    private String Vendororder;
    private String OrderNo;
    private String ErrorCode;
    private String ErrorMessage;

    public String getVendororder() {
        return Vendororder;
    }

    public void setVendororder(String vendororder) {
        Vendororder = vendororder;
    }

    public String getOrderNo() {
        return OrderNo;
    }

    public void setOrderNo(String orderNo) {
        OrderNo = orderNo;
    }

    public String getErrorCode() {
        return ErrorCode;
    }

    public void setErrorCode(String errorCode) {
        ErrorCode = errorCode;
    }

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        ErrorMessage = errorMessage;
    }
}
