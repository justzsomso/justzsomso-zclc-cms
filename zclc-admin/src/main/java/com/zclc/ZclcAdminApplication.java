package com.zclc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 博客后台启动类
 */
@SpringBootApplication
@MapperScan("com.zclc.mapper")
public class ZclcAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZclcAdminApplication.class, args);
    }
}
