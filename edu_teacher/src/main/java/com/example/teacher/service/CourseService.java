package com.example.teacher.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.teacher.pojo.Course;
import com.example.teacher.pojo.query.CourseQuery;
import com.example.teacher.pojo.vo.CoursePublishVo;
import com.example.teacher.pojo.vo.CourseVo;
import com.example.teacher.pojo.vo.CourseWebVo;
import com.example.teacher.result.Result;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author jiangsanjin
 * @since 2021-03-26
 */
public interface CourseService extends IService<Course> {



    /**
     * 保存课程
     * @param courseVo
     * @return
     */
    String saveCourseVo(CourseVo courseVo);

    /**
     * 根据课程ID获取课程基本信息和描述
     * @param id
     * @return
     */
    CourseVo getCourseVoById(String id);

    /**
     * 修改课程基本信息
     * @param vo
     * @return
     */
    int updateVo(CourseVo vo);

    /**
     * 根据搜索条件分页查询
     * @param objectPage
     * @param courseQuery
     */
    void getPageList(Page<Course> objectPage, CourseQuery courseQuery);

    /**
     * 根据课程ID删除课程信息
     * @param id
     * @return
     */
    Boolean deleteById(String id);



    /**
     * 根据课程ID查询发布课程的详情
     * @param id
     * @return
     */
    CoursePublishVo getCoursePublishVoById(String id);

    /**
     * 根据 id 修改课程状态
     * @param id
     * @return
     */
    Boolean updateStatusById(String id);

    /**
     * 课程详情页面的数据
     * @param id
     * @return
     */
    CourseWebVo getCourseWebVoById(String id);


    /**
     * 根据课程id更改销售量
     * @param id
     */
    void updateBuyCountById(String id);
}
