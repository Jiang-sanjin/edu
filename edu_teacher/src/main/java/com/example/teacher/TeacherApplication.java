package com.example.teacher;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@MapperScan("com.example.teacher.mapper")
@ComponentScan(basePackages = {"com.example.teacher.handler","com.example.teacher.client","com.example.teacher"})
@SpringBootApplication
@EnableFeignClients   //服务调用
@EnableEurekaClient

public class TeacherApplication {
    public static void main(String[] args) {
        SpringApplication.run(TeacherApplication.class,args);
    }
}
