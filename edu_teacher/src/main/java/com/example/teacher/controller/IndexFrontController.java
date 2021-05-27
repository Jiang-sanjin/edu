package com.example.teacher.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.teacher.pojo.Course;
import com.example.teacher.pojo.Teacher;
import com.example.teacher.result.Result;
import com.example.teacher.service.CourseService;
import com.example.teacher.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/indexFront")
public class IndexFrontController {

    @Autowired
    CourseService courseService;
    @Autowired
    TeacherService teacherService;

    //查前8条热门课程 4名师
    @GetMapping("index")
    public Result index(){
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("view_count");
        wrapper.last("limit 8");
        List<Course> courseList = courseService.list(wrapper);
        QueryWrapper<Teacher> wrapper1 = new QueryWrapper<>();
        wrapper1.orderByAsc("id");
        wrapper1.last("limit 4");
        List<Teacher> teacherList = teacherService.list(wrapper1);
        return Result.ok().data("courseList",courseList).data("teacherList",teacherList);
    }
}
