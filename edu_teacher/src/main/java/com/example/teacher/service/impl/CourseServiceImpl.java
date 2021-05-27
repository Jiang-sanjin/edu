package com.example.teacher.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.teacher.mapper.CourseDescriptionMapper;
import com.example.teacher.mapper.CourseMapper;
import com.example.teacher.pojo.Course;
import com.example.teacher.pojo.CourseDescription;
import com.example.teacher.pojo.Teacher;
import com.example.teacher.pojo.query.CourseQuery;
import com.example.teacher.pojo.vo.CoursePublishVo;
import com.example.teacher.pojo.vo.CourseVo;
import com.example.teacher.pojo.vo.CourseWebVo;
import com.example.teacher.result.Result;
import com.example.teacher.service.ChapterService;
import com.example.teacher.service.CourseDescriptionService;
import com.example.teacher.service.CourseService;
import com.example.teacher.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author jiangsanjin
 * @since 2021-03-26
 */
@Service
@Transactional
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {


    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private CourseDescriptionMapper courseDescriptionMapper;

    @Autowired
    private CourseDescriptionService courseDescriptionService;

    @Autowired
    private VideoService videoService;

    @Autowired
    private ChapterService chapterService;

    /**
     * 保存课程
     *
     * @param courseVo
     * @return
     */
    @Override
    public String saveCourseVo(CourseVo courseVo) {

        //添加课程
        courseMapper.insert(courseVo.getEduCourse());

        // 获取课程id
        String id = courseVo.getEduCourse().getId();
        // 设置课程id
        courseVo.getCourseDescription().setId(id);

        //添加课程描述
        courseDescriptionMapper.insert(courseVo.getCourseDescription());
        return id;
    }

    /**
     * 根据课程ID获取课程基本信息和描述
     *
     * @param id
     * @return
     */
    @Override
    public CourseVo getCourseVoById(String id) {
        //创建一个Vo对象
        CourseVo vo = new CourseVo();
        //根据课程ID获取课程对象 EduCourse
        Course course = courseMapper.selectById(id);
        if (course == null) {
            return vo;
        }
        // 把课程加到vo对象中
        vo.setEduCourse(course);
        //根据课程ID获取课程描述
        CourseDescription courseDescription = courseDescriptionService.getById(id);
        //把描述加在vo对象中
        if (courseDescription == null) {
            return vo;
        }
        vo.setCourseDescription(courseDescription);

        return vo;
    }

    /**
     * 修改课程基本信息
     *
     * @param vo
     * @return
     */
    @Override
    public int updateVo(CourseVo vo) {
        //1、 先判断ID是否存在、如果不存在直接放回false
        if (StringUtils.isEmpty(vo.getEduCourse().getId())) {
            return 0;
        }
        //2、修改course
        int i = courseMapper.updateById(vo.getEduCourse());
        if (i <= 0) {
            return 0;
        }
        //3、修改courseDesc
        vo.getCourseDescription().setId(vo.getEduCourse().getId());
        int b = courseDescriptionMapper.updateById(vo.getCourseDescription());
        return b;
    }

    /**
     * 根据搜索条件分页查询
     * @param objectPage
     * @param courseQuery
     */
    @Override
    public void getPageList(Page<Course> objectPage, CourseQuery courseQuery) {
        QueryWrapper<Course> wrapper = new QueryWrapper<>();

        if(courseQuery == null){
            courseMapper.selectPage(objectPage,wrapper);
        }
        String subjectId = courseQuery.getSubject_id();
        String subjectParentId = courseQuery.getSubject_parent_id();
        String teacherId = courseQuery.getTeacher_id();
        String title = courseQuery.getTitle();

        if(!StringUtils.isEmpty(subjectId)){
            wrapper.eq("subject_id",subjectId);
        }
        if(!StringUtils.isEmpty(subjectParentId)){
            wrapper.eq("subject_parent_id",subjectParentId);
        }
        if(!StringUtils.isEmpty(teacherId)){
            wrapper.eq("teacher_id",teacherId);
        }
        if(!StringUtils.isEmpty(title)){
            wrapper.like("title",title);
        }
        if(!StringUtils.isEmpty(courseQuery.getBuy_count_sort())){ //关注度(销量)
            wrapper.orderByDesc("buy_count");
        }
        if(!StringUtils.isEmpty(courseQuery.getGmt_modified_sort())){//时间
            wrapper.orderByDesc("gmt_modified");
        }
        if(!StringUtils.isEmpty(courseQuery.getPrice_sort())){//价格
            wrapper.orderByDesc("price");
        }

        courseMapper.selectPage(objectPage,wrapper);
    }

    /**
     * 根据课程ID删除课程信息
     * @param id
     * @return
     */
    @Override
    public Boolean deleteById(String id) {

        // TODO 删除课程相关的小节
        //根据课程ID删除小节
        videoService.removeVideoByCourseId(id);
        // TODO 删除课程相关的章节
        chapterService.removeChapterById(id);

        // 删除描述的id
        boolean b = courseDescriptionService.removeById(id);
        if(!b){
            return false;
        }
        // 删除基本信息
        int i = courseMapper.deleteById(id);
        return i == 1;
    }

    /**
     * 根据课程ID查询发布课程的详情
     * @param id
     * @return
     */
    @Override
    public CoursePublishVo getCoursePublishVoById(String id) {
        CoursePublishVo vo = courseMapper.getCoursePublishVoById(id);
        return vo;

    }

    /**
     * 根据 id 修改课程状态
     * @param id
     * @return
     */
    @Override
    public Boolean updateStatusById(String id) {
        Course course = new Course();
        course.setId(id);
        course.setStatus("Normal");   //Normal为发布
        int i = courseMapper.updateById(course);
        return i == 1;
    }

    /**
     * 课程详情页面的数据
     * @param id
     * @return
     */
    @Override
    public CourseWebVo getCourseWebVoById(String id) {
        CourseWebVo courseWebVo = courseMapper.getCourseWebVoById(id);
        return courseWebVo;
    }

    /**
     * 根据课程id更改销售量
     * @param id
     */
    @Override
    public void updateBuyCountById(String id) {
        Course course = courseMapper.selectById(id);
        course.setBuy_count(course.getBuy_count() + 1);
        courseMapper.updateById(course);
    }


}
