package com.qiyuan.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.qiyuan.security",
        "com.qiyuan.web"
})
@MapperScan(basePackages = {
        "com.qiyuan.web.dao",
        "com.qiyuan.security.dao"
})
public class QiyuanApplication {

    public static void main(String[] args) {
        SpringApplication.run(QiyuanApplication.class, args);
    }

}
