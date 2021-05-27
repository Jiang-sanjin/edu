package com.example.statistics;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@MapperScan("com.example.statistics.mapper")
@SpringBootApplication
@EnableEurekaClient  //扫描服务
@EnableFeignClients //服务调用
@EnableScheduling //开启定时任务
public class StatisticsApplication {

    public static void main(String[] args) {
        SpringApplication.run(StatisticsApplication.class, args);
    }
}
