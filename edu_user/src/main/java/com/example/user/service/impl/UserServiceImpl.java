package com.example.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.example.user.pojo.User;
import com.example.user.mapper.UserMapper;
import com.example.user.pojo.vo.ChangeVo;
import com.example.user.pojo.vo.LoginVo;
import com.example.user.pojo.vo.RegisterVo;
import com.example.user.result.Result;
import com.example.user.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.user.util.JwtUtils;
import com.example.user.util.MD5;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author jiangsanjin
 * @since 2021-04-01
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private UserMapper userMapper;


    /**
     * 会员登录
     *
     * @param loginVo
     * @return
     */
    @Override
    public Result login(LoginVo loginVo) {
        String email = loginVo.getEmail();
        String password = loginVo.getPassword();

        //校验邮箱是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email);
        User member = userMapper.selectOne(queryWrapper);
        //用户存在，密码正确，未被禁用
        if (null != member && MD5.encrypt(password).equals(member.getPassword()) && !member.getIs_disabled()) {

            //使用JWT生成token字符串
            String token = JwtUtils.getJwtToken(member.getId(), member.getNickname());
            return Result.ok().data("token", token).message("登录成功");
        }

        return Result.error().message("登录失败");


    }

    /**
     * 会员注册
     *
     * @param registerVo
     */
    @Override
    public Result register(RegisterVo registerVo) {
//获取注册的数据 校验参数
        String code = registerVo.getCode(); //验证码
        String email = registerVo.getEmail(); // 邮箱
        String nickname = registerVo.getNickname(); //昵称
        String password = registerVo.getPassword(); //密码

        //非空判断
        if (StringUtils.isEmpty(email) || StringUtils.isEmpty(password)
                || StringUtils.isEmpty(code) || StringUtils.isEmpty(nickname)) {
            return Result.error().message("注册失败");
        }

        //判断验证码
        //获取redis验证码
        String redisCode = redisTemplate.opsForValue().get(email);
        if (!code.equals(redisCode)) {
            return Result.error().message("验证码错误");
        }

        //判断邮箱是否重复，表里面存在相同手机号不进行添加
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("email", email);
        Integer count = userMapper.selectCount(wrapper);
        if (count > 0) {
            return Result.error().message("该邮箱已被注册");
        }

        //数据添加数据库中
        User member = new User();
        member.setEmail(email);
        member.setNickname(nickname);
        member.setPassword(MD5.encrypt(password));//密码需要j进行MD5加密
        member.setIs_disabled(false);//用户不禁用
        member.setAvatar("https://img.sanjin.work/avatar/2021/04/09/997ED5889A4ABD86C0B74FB93462F72C.jpg");
        userMapper.insert(member);

        return Result.ok().message("注册成功");


    }

    /**
     * 更改密码
     *
     * @param changeVo
     */
    @Override
    public Result changePasswd(ChangeVo changeVo) {
        //获取注册的数据 校验参数
        String code = changeVo.getCode(); //验证码
        String email = changeVo.getEmail(); //邮箱号
        String password = changeVo.getPassword(); //密码

        //非空判断
        if (StringUtils.isEmpty(email) || StringUtils.isEmpty(password)
                || StringUtils.isEmpty(code)) {
            return Result.error().message("修改密码失败");
        }

        //判断验证码
        //获取redis验证码
        String redisCode = redisTemplate.opsForValue().get(email);
        if (!code.equals(redisCode)) {
            return Result.error().message("验证码错误");
        } else {
            //修改密码
            QueryWrapper<User> wrapper = new QueryWrapper<>();
            wrapper.eq("email",email);
            User user = userMapper.selectOne(wrapper);
            user.setPassword(MD5.encrypt(password));
            userMapper.updateById(user);
            return Result.ok().message("修改密码成功");
        }


    }


    /**
     * 统计某一天注册的人数
     *
     * @param day
     * @return
     */
    @Override
    public Integer getCountRegister(String day) {

        return userMapper.getCountRegister(day);
    }
}
