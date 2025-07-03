package com.qiyuan.web.enums;

import com.qiyuan.security.exception.ApiException;

public enum InvoiceType {
    PAPER("paper", "紙本發票"),
    MOBILE("mobile", "手機條碼"),
    CITIZEN("citizen", "自然人憑證"),
    DONATION("donation", "捐贈發票"),
    COMPANY("company", "三聯式/公司戶");

    private final String value;
    private final String label;

    InvoiceType(String value, String label) {
        this.value = value;
        this.label = label;
    }

    public String getValue() { return value; }
    public String getLabel() { return label; }

    public static InvoiceType fromValue(String value) {
        for (InvoiceType t : values()) {
            if (t.value.equalsIgnoreCase(value)) return t;
        }
        throw new ApiException("未知發票型別: " + value);
    }
}
