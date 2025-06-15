package com.qiyuan.web.entity;

public class Lantern {
    private String id;

    private String name;

    private String subtitle;

    private String iconName;

    private String labelRight;

    private String qaJson;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    public String getLabelRight() {
        return labelRight;
    }

    public void setLabelRight(String labelRight) {
        this.labelRight = labelRight;
    }

    public String getQaJson() {
        return qaJson;
    }

    public void setQaJson(String qaJson) {
        this.qaJson = qaJson;
    }
}