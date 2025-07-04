package com.qiyuan.web.enums;

public enum TokenType {
    ACCESS("access"),
    REFRESH("refresh"),
    SSO("sso");

    private final String value;

    TokenType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static TokenType fromValue(String value) {
        for (TokenType t : values()) {
            if (t.value.equalsIgnoreCase(value)) {
                return t;
            }
        }
        throw new IllegalArgumentException("Unknown token type: " + value);
    }
}
