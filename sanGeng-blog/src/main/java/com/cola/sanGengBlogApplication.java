package com.cola;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Author: cola99year
 * @Date: 2022/10/10 17:32
 */
@SpringBootApplication
@MapperScan("com.cola.mapper")
@EnableScheduling
public class sanGengBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(sanGengBlogApplication.class,args);
    }
}
