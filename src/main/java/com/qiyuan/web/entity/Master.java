package com.qiyuan.web.entity;

public class Master {
    private String code;

    private String name;

    private String title;

    private String mainStar;

    private String bio;

    private String experience;

    private String personalItems;

    private String servicesJson;

    private Boolean status;

    private String imageExt;

    private Byte sort;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMainStar() {
        return mainStar;
    }

    public void setMainStar(String mainStar) {
        this.mainStar = mainStar;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getPersonalItems() {
        return personalItems;
    }

    public void setPersonalItems(String personalItems) {
        this.personalItems = personalItems;
    }

    public String getServicesJson() {
        return servicesJson;
    }

    public void setServicesJson(String servicesJson) {
        this.servicesJson = servicesJson;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getImageExt() {
        return imageExt;
    }

    public void setImageExt(String imageExt) {
        this.imageExt = imageExt;
    }

    public Byte getSort() {
        return sort;
    }

    public void setSort(Byte sort) {
        this.sort = sort;
    }
}