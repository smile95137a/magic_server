package com.qiyuan.web.entity;

public class Offering {
    private String id;

    private String imageUrl;

    private Byte points;

    private Short price;

    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Byte getPoints() {
        return points;
    }

    public void setPoints(Byte points) {
        this.points = points;
    }

    public Short getPrice() {
        return price;
    }

    public void setPrice(Short price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}