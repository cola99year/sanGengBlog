package com.cola.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
    @Bean
    public Docket customDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.cola.controller"))
                .build();
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("可贺", "http://www.4399.com", "1620921710@qq.com");
        return new ApiInfoBuilder()
                .title("三更个人博客项目")
                .description("一个很不错的前后端分离博客项目！")
                .contact(contact)   // 联系方式
                .version("MIUI14.0")  // 版本
                .build();
    }
}