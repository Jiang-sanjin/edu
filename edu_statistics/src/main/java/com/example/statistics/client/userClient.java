package com.example.statistics.client;

import com.example.statistics.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Component
@FeignClient("edu-user")
public interface userClient {

    @GetMapping("/ucenter/countRegister/{day}")
    public Result getCountRegister(@PathVariable("day") String day);  //加上参数的名字   避免报错
}
