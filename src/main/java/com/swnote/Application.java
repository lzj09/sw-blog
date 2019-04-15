package com.swnote;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 项目启动类
 * 
 * @author lzj
 * @since 1.0
 * @date [2019-04-04]
 */
@SpringBootApplication
@MapperScan(basePackages = "com.swnote")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}