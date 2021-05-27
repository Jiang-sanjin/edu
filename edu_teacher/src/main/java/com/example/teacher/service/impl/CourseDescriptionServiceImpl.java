package com.example.teacher.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.teacher.mapper.CourseDescriptionMapper;
import com.example.teacher.pojo.CourseDescription;
import com.example.teacher.service.CourseDescriptionService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程简介 服务实现类
 * </p>
 *
 * @author jiangsanjin
 * @since 2021-03-26
 */
@Service
public class CourseDescriptionServiceImpl extends ServiceImpl<CourseDescriptionMapper, CourseDescription> implements CourseDescriptionService {

}
