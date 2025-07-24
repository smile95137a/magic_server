package com.qiyuan.web.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.qiyuan.security.exception.ApiException;

public enum InvoiceType {
    CITIZEN("citizen", "個人電子發票"),
    MOBILE("mobile", "手機條碼載具"),
    DONATION("donation", "捐贈發票"),
    COMPANY("company", "三聯式/公司戶");

    private final String value;
    private final String label;

    InvoiceType(String value, String label) {
        this.value = value;
        this.label = label;
    }

    @JsonValue
    public String getValue() { return value; }
    public String getLabel() { return label; }

    public static InvoiceType fromValue(String value) {
        for (InvoiceType t : values()) {
            if (t.value.equalsIgnoreCase(value)) return t;
        }
        throw new ApiException("未知發票型別: " + value);
    }

    @JsonCreator
    public static InvoiceType of(String value) {
        for (InvoiceType type : InvoiceType.values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }
}
