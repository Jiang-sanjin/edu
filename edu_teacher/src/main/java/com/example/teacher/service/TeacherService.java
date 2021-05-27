package com.example.teacher.service;

import com.example.teacher.pojo.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.teacher.pojo.query.TeacherQuery;
import com.example.teacher.result.Result;

import java.util.List;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author jiangsanjin
 * @since 2021-03-19
 */
public interface TeacherService extends IService<Teacher> {

    /**
     * 分页查询老师(根据姓名查询)
     * @param page
     * @param limit
     * @param teacherQuery
     * @return
     */
    Result getTeacherListBypage(Integer page, Integer limit, TeacherQuery teacherQuery);

    /**
     * 查询老师列表
     * @return
     */
    List<Teacher> getTeacherList();

    /**
     * 根据教师id查询老师
     * @param id
     * @return
     */
    Result getTeacherById(String id);
}
