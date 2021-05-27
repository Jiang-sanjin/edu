package com.example.teacher.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.teacher.pojo.Teacher;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.teacher.pojo.query.TeacherQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 讲师 Mapper 接口
 * </p>
 *
 * @author jiangsanjin
 * @since 2021-03-19
 */
@Mapper
@Repository
public interface TeacherMapper extends BaseMapper<Teacher> {

    /**
     * 分页查询老师(根据姓名等查询)
     * @param currentPage
     * @param teacherQuery
     * @return
     */
    IPage<Teacher> getTeacherListBypage(Page<Teacher> currentPage,  TeacherQuery teacherQuery);

    /**
     * 查询老师列表
     * @return
     */
    List<Teacher> getTeacherList();

    /**
     * 根据老师id查询
     * @param id
     */
    Teacher getTeacherById(String id);
}
