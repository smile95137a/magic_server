package com.qiyuan.web.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.qiyuan.security.exception.ApiException;

public enum SourceTypeEnum {
    REAL("R", "實體商品訂單"),
    LANTERN("L", "點燈"),
    OFFERING("O", "供品"),
    MASTER_SERVICE("M", "老師服務"),
    GOD("G", "請神");

    private final String code;
    private final String label;

    SourceTypeEnum(String code, String label) {
        this.code = code;
        this.label = label;
    }

    @JsonValue
    public String getCode() { return code; }
    public String getLabel() { return label; }

    public static SourceTypeEnum fromCode(String code) {
        for (SourceTypeEnum s : values()) {
            if (s.getCode().equalsIgnoreCase(code)) return s;
        }
        throw new IllegalArgumentException("未知來源類型: " + code);
    }

    @JsonCreator
    public static SourceTypeEnum fromValue(String value) {
        for (SourceTypeEnum t : values()) {
            if (t.getCode().equalsIgnoreCase(value)) return t;
        }
        throw new ApiException("未知類型: " + value);
    }
}

