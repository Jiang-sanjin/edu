package com.example.teacher.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.teacher.pojo.Course;
import com.example.teacher.pojo.query.CourseQuery;
import com.example.teacher.pojo.vo.CoursePublishVo;
import com.example.teacher.pojo.vo.CourseWebVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.sql.Wrapper;


/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author jiangsanjin
 * @since 2021-03-26
 */
@Repository
public interface CourseMapper extends BaseMapper<Course> {


    /**
     * 根据课程ID查询发布课程的详情
     * @param id
     * @return
     */
    CoursePublishVo getCoursePublishVoById(String id);


    /**
     * 课程详情页面的数据
     * @param id
     * @return
     */
    CourseWebVo getCourseWebVoById(String id);

    @Update("update edu_course c set c.view_count = c.view_count + 1  ${ew.customSqlSegment}")
    void updateViewCount(@Param(Constants.WRAPPER) QueryWrapper wrapper);
}
