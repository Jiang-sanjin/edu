package com.example.user.mapper;

import com.example.user.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author jiangsanjin
 * @since 2021-04-01
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

    /**
     * 统计某一天注册的人数
     * @param day
     * @return
     */
    Integer getCountRegister(String day);
}
