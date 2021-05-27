package com.example.teacher.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.example.teacher.mapper.CourseMapper;
import com.example.teacher.pojo.Course;
import com.example.teacher.pojo.query.CourseQuery;
import com.example.teacher.pojo.vo.CoursePublishVo;
import com.example.teacher.pojo.vo.CourseVo;
import com.example.teacher.pojo.vo.CourseWebVo;
import com.example.teacher.pojo.vo.OneChapter;
import com.example.teacher.result.Result;
import com.example.teacher.service.ChapterService;
import com.example.teacher.service.CourseService;
import com.example.teacher.util.JwtUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author jiangsanjin
 * @since 2021-03-26
 */
@RestController
@RequestMapping("/course")
@CrossOrigin
public class
CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private ChapterService chapterService;

    @Autowired
    private CourseMapper courseMapper;



    @ApiOperation(value = "保存课程")
    @PostMapping("saveVo")
    public Result saveCourse(@RequestBody CourseVo courseVo) { //接收课程和描述对象
        //接收课程和描述对象
        String courseId = courseService.saveCourseVo(courseVo);

        return Result.ok().data("id", courseId);

    }


    @ApiOperation(value = "根据课程ID获取课程基本信息和描述")
    @GetMapping("{id}")
    public Result getCourseVoById(@PathVariable String id) {
        CourseVo vo = courseService.getCourseVoById(id);
        return Result.ok().data("courseInfo", vo);
    }

    @ApiOperation(value = "修改课程基本信息")
    @PutMapping("updateVo")
    public Result updateVo(@RequestBody CourseVo vo) {
        int flag = courseService.updateVo(vo);
        if (flag != 0) {
            return Result.ok();
        } else {
            return Result.error();
        }
    }

    @ApiOperation(value = "根据搜索条件分页查询")
    @PostMapping("{page}/{limit}")
    public Result getCoursePageList(@PathVariable Integer page,
                                    @PathVariable Integer limit,
                                    @RequestBody CourseQuery courseQuery) {


        Page<Course> objectPage = new Page<>(page, limit);
        courseService.getPageList(objectPage, courseQuery);
        return Result.ok()
                .data("rows", objectPage.getRecords())
                .data("total", objectPage.getTotal());
    }


    @ApiOperation(value = "根据课程ID删除课程信息")
    @DeleteMapping("{id}")
    public Result deleteById(@PathVariable String id) {
        Boolean flag = courseService.deleteById(id);
        if (flag) {
            return Result.ok();
        } else {
            return Result.error();
        }
    }


    @ApiOperation(value = "根据课程ID查询发布课程的详情")
    @GetMapping("vo/{id}")
    public Result getCoursePublishVoById(@PathVariable String id) {
        CoursePublishVo vo = courseService.getCoursePublishVoById(id);

        if (vo != null) {
            return Result.ok().data("coursePublishVo", vo);
        } else {
            return Result.error();
        }


    }

    @ApiOperation(value = "根据 id 修改课程状态")
    @GetMapping("updateStatusById/{id}")
    public Result updateStatusById(@PathVariable String id) {
        Boolean flag = courseService.updateStatusById(id);
        if (flag) {
            return Result.ok();
        } else {
            return Result.error();
        }
    }

    @ApiOperation(value = "根据课程ID查询课程(详情页面的数据,章节小节)")
    @PostMapping("WebVo/{id}")
    public Result getCourseWebVoById(@PathVariable String id) {
        // 根据id查询课程信息
        CourseWebVo courseWebVo = courseService.getCourseWebVoById(id);
        // 根据课程id查询章节小节
        List<OneChapter> chapterVideoList = chapterService.getChapterAndVideoById(id);
        if (courseWebVo != null) {
            return Result.ok().data("courseWebVo", courseWebVo).data("chapterVideoList",chapterVideoList);
        } else {
            return Result.error();
        }
    }

    //根据课程id查询课程信息(返回给订单)
    @PostMapping("getCourseInfoOrder/{courseId}")
    public CourseWebVo getCourseInfoOrder(@PathVariable("courseId") String courseId){
        CourseWebVo baseCourseInfo = courseService.getCourseWebVoById(courseId);
        CourseWebVo courseWebVo = new CourseWebVo();
        BeanUtils.copyProperties(baseCourseInfo,courseWebVo);
        return courseWebVo;
    }

    @ApiOperation(value = "增加游览次数")
    @GetMapping("updateViewCount/{id}")
    public void updateViewCount(@PathVariable String id) {
        //增加游览次数

        courseMapper.updateViewCount(new QueryWrapper<Course>().eq("id", id));
    }

    @ApiOperation("根据课程id更改销售量")
    @GetMapping("updateBuyCount/{id}")
    public Result updateBuyCountById(@PathVariable String id){
        courseService.updateBuyCountById(id);
        return Result.ok();
    }

}
