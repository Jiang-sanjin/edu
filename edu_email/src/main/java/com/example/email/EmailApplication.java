package com.example.email;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;


@EnableEurekaClient  //扫描服务
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan("com.example")
public class EmailApplication {
    public static void main(String[] args) {
        SpringApplication.run(EmailApplication.class,args);
    }
}
