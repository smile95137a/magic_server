package com.qiyuan.web;

import com.chl.enums.TableSelectionMode;
import com.chl.generator.DatabaseSchemaGenerator;
import com.chl.results.GenerateResult;
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
//        generateCode05();
    }


    private static void generateCode05() {

        DatabaseSchemaGenerator generator = new DatabaseSchemaGenerator()
                .setDatabase("com.microsoft.sqlserver.jdbc.SQLServerDriver",
                        "jdbc:sqlserver://localhost:11433;databaseName=qiyuan;encrypt=true;trustServerCertificate=true;",
                        "sa", "1qaz@WSX")
                .setModelOutput("com.qiyuan.web.entity")
                .setMapperOutput("com.qiyuan.web.dao")
                .setXmlOutput("mappers")
                .enableComments(false)
                .setTableSelection(TableSelectionMode.INCLUDE,
                        "banner")
                .enableToString(false);  // 排除所有視圖;

        GenerateResult result = generator.generate();

        if (result.isSuccess()) {
            result.getGeneratedFiles().forEach(System.out::println);
        } else {
            System.err.println("✘ 生成失敗：");
        }
    }



}
