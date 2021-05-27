package com.example.teacher.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.teacher.pojo.Course;
import com.example.teacher.pojo.Teacher;
import com.example.teacher.mapper.TeacherMapper;
import com.example.teacher.pojo.query.TeacherQuery;
import com.example.teacher.result.Result;
import com.example.teacher.service.CourseService;
import com.example.teacher.service.TeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author jiangsanjin
 * @since 2021-03-19
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {


    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private CourseService courseService;
    /**
     * 分页查询老师(条件查询查询)
     * @param page
     * @param limit
     * @param teacherQuery
     * @return
     */
    @Override
    public Result getTeacherListBypage(Integer page, Integer limit, TeacherQuery teacherQuery) {

        Page<Teacher> currentPage = new Page<>(page,limit);
        IPage<Teacher> teacherListBypage = teacherMapper.getTeacherListBypage(currentPage, teacherQuery);

        return Result.ok().data("total",teacherListBypage.getTotal()).data("rows",teacherListBypage.getRecords());
    }

    /**
     * 查询老师列表
     * @return
     */
    @Override
    public List<Teacher> getTeacherList() {
        return teacherMapper.getTeacherList();
    }

    /**
     * 根据教师id查询
     * @param id
     * @return
     */
    @Override
    public Result getTeacherById(String id) {
        // 1.查询 教师的基本信息
        Teacher teacher = teacherMapper.getTeacherById(id);
        // 2. 查询 教师的课程信息
        //2 根据讲师id查询课程信息
        QueryWrapper<Course> wrapper = new QueryWrapper();
        wrapper.eq("teacher_id",id);
        List<Course> courseList = courseService.list(wrapper);

        if ( null != teacher && null!= courseList){
            return Result.ok().data("teacher",teacher).data("courseList",courseList);
        }

        return Result.error();
    }
}
