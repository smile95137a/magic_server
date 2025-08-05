package com.qiyuan.web.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "gomypay.logistics")
@Configuration
@Data
public class GomypayLogisticsProperties {
    private String baseUrl;
    private String customerId;
    private String customerPassword;

    public String getStoreCreateUri() {
        return baseUrl + "/LogisticsAPI.aspx";
    }

    public String getExpressCreateUri() {
        return baseUrl + "/Api/SF/SFCreateAPI.aspx";
    }
}
