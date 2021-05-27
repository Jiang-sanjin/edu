package com.example.order.client;


import com.example.order.pojo.vo.User;
import com.example.order.result.Result;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient("edu-user")
public interface UserClient {
    //根据用户id获取用户信息
    @PostMapping(value = "/ucenter/getUserInfoOrder/{id}")
    public User getUserInfoOrder(@PathVariable("id") String id);


}
