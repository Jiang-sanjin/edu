package com.example.email.controller;


import com.example.email.result.Result;
import com.example.email.service.EmailService;
import com.example.email.utils.RandomUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@CrossOrigin
@RequestMapping("/email")
@RestController
public class EmailController {
    @Autowired
    EmailService emailService;

    @Autowired
    RedisTemplate<String,String> redisTemplate;
    //发送短信的方法
    @GetMapping("send/{email}")
    public Result sendEmail(@PathVariable String email){
        //1 从redis中获取验证码 如果获取到 则直接返回
        String s = redisTemplate.opsForValue().get(email);
        if(!StringUtils.isEmpty(s)) return Result.ok();
        //2 如果redis获取不到 则进行邮件发送
        //生成随机值 传递给邮件进行发送
        String code = RandomUtil.getFourBitRandom(); //生成4位随机数

        //调用service方法进行邮件发送
        boolean flag = EmailService.sendEmail("验证码："+code+"（有效时间为5分钟）",email);
        if(flag) {
            //发送成功 把验证码加入redis 并且设置有效时间   TimeUnit.MINUTES分钟
            redisTemplate.opsForValue().set(email,code,5, TimeUnit.MINUTES);
            return Result.ok();
        }
        return Result.error();
    }
}
