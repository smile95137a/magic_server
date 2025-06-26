package com.qiyuan.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "security")
public class SecurityProperties {
    private List<String> publicEndpoints = new ArrayList<>();
    private ResponseProperties response = new ResponseProperties();

    public List<String> getPublicEndpoints() {
        return publicEndpoints;
    }

    public void setPublicEndpoints(List<String> publicEndpoints) {
        this.publicEndpoints = publicEndpoints;
    }

    public ResponseProperties getResponse() {
        return response;
    }

    public void setResponse(ResponseProperties response) {
        this.response = response;
    }

    public static class ResponseProperties {
        private List<String> excludePaths = new ArrayList<>();

        public List<String> getExcludePaths() {
            return excludePaths;
        }

        public void setExcludePaths(List<String> excludePaths) {
            this.excludePaths = excludePaths;
        }
    }
}
