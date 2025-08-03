package com.qiyuan.web.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "gomypay.einvoice")
@Data
public class GomypayEinvoiceProperties {
    private String baseUrl;
    private String customerNo;
    private String pwd;

    public String getIssueUrl() {
        return baseUrl + "/AddInvoice";
    }

    public String getCheckInfoUrl() {
        return baseUrl + "/Check";
    }

}
