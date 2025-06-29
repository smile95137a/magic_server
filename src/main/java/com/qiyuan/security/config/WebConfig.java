package com.qiyuan.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final ImagePathMappingConfig  mappingConfig;

    @Autowired
    public WebConfig(ImagePathMappingConfig mappingConfig) {
        this.mappingConfig = mappingConfig;
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        addImageResourceHandler(registry, "product");
    }

    private void addImageResourceHandler(ResourceHandlerRegistry registry, String key) {
        String urlPrefix = mappingConfig.getUrlPrefix(key);
        String physicalDir = mappingConfig.getImagePath(key);
        if (!urlPrefix.endsWith("/")) urlPrefix += "/";
        if (!physicalDir.endsWith(File.separator)) physicalDir += File.separator;

        registry.addResourceHandler(urlPrefix + "**")
                .addResourceLocations("file:" + physicalDir);
    }
}
