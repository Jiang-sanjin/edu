package com.example.user.service;

import com.example.user.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.user.pojo.vo.ChangeVo;
import com.example.user.pojo.vo.LoginVo;
import com.example.user.pojo.vo.RegisterVo;
import com.example.user.result.Result;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author jiangsanjin
 * @since 2021-04-01
 */
public interface UserService extends IService<User> {

    /**
     * 统计某一天注册的人数
     * @param day
     * @return
     */
    Integer getCountRegister(String day);

    /**
     * 会员登录
     * @param loginVo
     * @return
     */
    Result login(LoginVo loginVo);

    /**
     * 会员注册
     * @param registerVo
     */
    Result register(RegisterVo registerVo);

    /**
     * 修改密码
     * @param changeVo
     */
    Result changePasswd(ChangeVo changeVo);
}
