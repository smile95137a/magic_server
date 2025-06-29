package com.qiyuan.web.enums;

public enum OrderStatus {
    CREATED("created", "已建立"),
    PAID("paid", "已付款"),
    PROCESSING("processing", "處理中"),
    SHIPPED("shipped", "已出貨"),
    DELIVERED("delivered", "已完成"),
    CANCELLED("cancelled", "已取消"),
    REFUNDED("refunded", "已退款");

    private final String value;
    private final String label;

    OrderStatus(String value, String label) {
        this.value = value;
        this.label = label;
    }

    public String getValue() { return value; }
    public String getLabel() { return label; }

    public static OrderStatus fromValue(String value) {
        for (OrderStatus s : values()) {
            if (s.value.equalsIgnoreCase(value)) return s;
        }
        throw new IllegalArgumentException("未知訂單狀態: " + value);
    }
}

