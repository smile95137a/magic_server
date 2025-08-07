package com.qiyuan.web.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Set;

public enum OrderStatus {
    CREATED("created", "已建立"),
    PAID("paid", "已付款"),
    PROCESSING("processing", "訂單準備中"),
    READY_TO_SHIP("ready_to_ship", "準備出貨"),
    SHIPPED("shipped", "已出貨"),
    DELIVERED("delivered", "已完成"),
    CANCELLED("cancelled", "已取消"),
    RETURNED("returned", "已退貨"),
    REFUNDED("refunded", "已退款");

    private final String value;
    private final String label;

    OrderStatus(String value, String label) {
        this.value = value;
        this.label = label;
    }

    @JsonValue
    public String getValue() { return value; }
    public String getLabel() { return label; }

    public static OrderStatus findByValue(String value) {
        for (OrderStatus s : values()) {
            if (s.value.equalsIgnoreCase(value)) return s;
        }
        throw new IllegalArgumentException("未知訂單狀態: " + value);
    }

    public static final Set<OrderStatus> BACKEND_SET = Set.of(
            OrderStatus.PROCESSING,
            OrderStatus.READY_TO_SHIP,
            OrderStatus.SHIPPED,
            OrderStatus.DELIVERED,
            OrderStatus.CANCELLED,
            OrderStatus.RETURNED,
            OrderStatus.REFUNDED
    );

    // 提供反查功能，可直接支援前端傳入 value，自動轉 enum
    @JsonCreator
    public static OrderStatus fromValue(String value) {
        for (OrderStatus status : values()) {
            if (status.value.equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("未知訂單狀態: " + value);
    }
}

