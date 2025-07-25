package com.qiyuan.web.enums;

public enum ProductImageType {
    MAIN("main", "主圖"),
    GALLERY("gallery", "圖庫"),
    DESCRIPTION("description", "描述圖");

    private final String folder;
    private final String label;

    ProductImageType(String folder, String label) {
        this.folder = folder;
        this.label = label;
    }

    public String getFolder() {
        return folder;
    }

    public String getLabel() {
        return label;
    }

    public static boolean isValid(String value) {
        if (value == null) return false;
        for (ProductImageType type : values()) {
            if (type.getFolder().equalsIgnoreCase(value)) return true;
        }
        return false;
    }

    public static ProductImageType fromString(String value) {
        for (ProductImageType type : values()) {
            if (type.getFolder().equalsIgnoreCase(value)) return type;
        }
        throw new IllegalArgumentException("Unknown ProductImageType: " + value);
    }
}
