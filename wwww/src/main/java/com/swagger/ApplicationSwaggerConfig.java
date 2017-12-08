package com.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by LaoWang on 2016/12/12.
 */

@EnableSwagger2 //必须存在
public class ApplicationSwaggerConfig {

    @Bean
    public Docket addUserDocket() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2);
        ApiInfo apiInfo = new ApiInfo(
                "APP接口列表",
                "内部人员开发文档",
                "本API服务于IOS和安卓端 V2.1.0",
                "无许可证",
                "RenKe Electronic Technology",
                "Druid连接池控制台",
                "/flyhkk-server/db/druid/index.html");
        docket.apiInfo(apiInfo);
        return docket;
    }
}
