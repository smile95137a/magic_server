package com.qiyuan.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "upload")
public class ImagePathMappingConfig {
    private Map<String, String> imagePath = new HashMap<>();
    private Map<String, String> urlPrefix = new HashMap<>();

    public String getImagePath(String key) { return imagePath.get(key); }
    public String getUrlPrefix(String key) { return urlPrefix.get(key); }

    public Map<String, String> getImagePath() {
        return imagePath;
    }

    public void setImagePath(Map<String, String> imagePath) {
        this.imagePath = imagePath;
    }

    public Map<String, String> getUrlPrefix() {
        return urlPrefix;
    }

    public void setUrlPrefix(Map<String, String> urlPrefix) {
        this.urlPrefix = urlPrefix;
    }
}

