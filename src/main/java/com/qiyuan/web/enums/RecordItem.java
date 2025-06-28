package com.qiyuan.web.enums;

public enum RecordItem {
    Offering("供品"),
    Lantern("點燈");

    private final String label;

    RecordItem(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return label;
    }
}
