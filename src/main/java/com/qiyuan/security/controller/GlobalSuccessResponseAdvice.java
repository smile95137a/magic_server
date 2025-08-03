package com.qiyuan.security.controller;

import com.qiyuan.security.config.SecurityProperties;
import com.qiyuan.security.response.ApiResponse;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;


@RestControllerAdvice
public class GlobalSuccessResponseAdvice implements ResponseBodyAdvice<Object> {
    private final SecurityProperties securityProperties;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    public GlobalSuccessResponseAdvice(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    @Override
    public boolean supports(MethodParameter returnType,
                            Class<? extends HttpMessageConverter<?>> converterType) {

        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {
        String path = request.getURI().getPath();
        if (securityProperties.getResponse().getExcludePaths()
                .stream().anyMatch(pattern -> pathMatcher.match(pattern, path))) {
            return body;
        }

        // 避免重複包裝
        if (body instanceof ApiResponse) {
            return body;
        } else if (body instanceof ResponseEntity) {
            return body;
        }

        return ApiResponse.success("執行成功", body);
    }
}
