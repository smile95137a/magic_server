package com.qiyuan.web.enums;

public enum PaymentEnum {
    PAID("paid", "已付款"),
    PENDING("pending", "待付款");

    private final String value;
    private final String label; // 給前端顯示用

    PaymentEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }

    public static PaymentEnum fromBoolean(Boolean paid) {
        return (paid != null && paid) ? PAID : PENDING;
    }

    public static PaymentEnum fromValue(String value) {
        for (PaymentEnum status : PaymentEnum.values()) {
            if (status.getValue().equalsIgnoreCase(value)) {
                return status;
            }
        }
       return PENDING;
    }
}
