package com.example.user.controller;


import com.example.user.pojo.User;
import com.example.user.pojo.vo.ChangeVo;
import com.example.user.pojo.vo.LoginVo;
import com.example.user.pojo.vo.RegisterVo;
import com.example.user.result.Result;
import com.example.user.service.UserService;
import com.example.user.util.JwtUtils;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author jiangsanjin
 * @since 2021-04-01
 */
@CrossOrigin
@RestController
@RequestMapping("/ucenter")
public class UserController {

    @Autowired
    private UserService userService;


    @ApiOperation(value = "会员登录")
    @PostMapping("login")
    public Result login(@RequestBody LoginVo loginVo) {

        return userService.login(loginVo);
    }


    @ApiOperation(value = "会员注册")
    @PostMapping("register")
    public Result registerUser(@RequestBody RegisterVo registerVo) {

        return userService.register(registerVo);
    }

    @ApiOperation(value = "根据token获取登录信息")
    @GetMapping("getMemberInfo")
    public Result getLoginInfo(HttpServletRequest request) {

        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        User user = userService.getById(memberId);
        if (null != user) {
            return Result.ok().data("userInfo", user);
        }

        return Result.error().message("获取信息失败");

    }


    //根据用户id获取用户信息
    @PostMapping("getUserInfoOrder/{id}")
    public User getUserInfoOrder(@PathVariable String id) {

        return userService.getById(id);

    }

    @ApiOperation(value = "根据用户id获取用户信息 个人中心用")
    @PostMapping("getUserInfo/{id}")
    public Result getUserInfo(@PathVariable String id) {
        User userInfo = userService.getById(id);
        return Result.ok().data("userInfo",userInfo);

    }


    @ApiOperation(value = "用户信息修改")
    @PostMapping("updateUser")
    public Result updateUser(@RequestBody User user) {
        boolean flag = userService.updateById(user);
        if(flag) {
            return Result.ok();
        } else {
            return Result.error();
        }
    }

    @ApiOperation(value = "更改密码")
    @PostMapping("change")
    public Result changePassword(@RequestBody ChangeVo changeVo) {

        return userService.changePasswd(changeVo);
    }

    @ApiOperation(value = "统计某一天注册的人数")
    @GetMapping("/countRegister/{day}")
    public Result getCountRegister(@PathVariable String day) {
        Integer count = userService.getCountRegister(day);
        return Result.ok().data("countRegister", count);
    }

}
