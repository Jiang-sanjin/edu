package com.example.teacher.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.teacher.pojo.Teacher;
import com.example.teacher.pojo.query.TeacherQuery;
import com.example.teacher.result.Result;
import com.example.teacher.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author jiangsanjin
 * @since 2021-03-19
 */
@Api(value = "老师管理")
@RestController
@RequestMapping("/teacher")
@CrossOrigin
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @ApiOperation(value = "获取所有老师列表")
    @GetMapping("/list")
    public Result getTeacherList() {
        List<Teacher> teacherList = teacherService.getTeacherList();
        if (teacherList.size() != 0) {
            return Result.ok().data("teacherList", teacherList);
        }
        return Result.error();
    }

    @ApiOperation(value = "根据 id 删除老师")
    @DeleteMapping("{id}")
    public Result removeTeacherById(@PathVariable String id) {
        //逻辑删除老师前  将老师的 level 设置为 null  解除与表edu_teacher_level的绑定
        teacherService.update(new UpdateWrapper<Teacher>().eq("id", id).set("level", null));
        if (teacherService.removeById(id)) {
            return Result.ok();
        }
        return Result.error();
    }

    @ApiOperation(value = "分页查询老师(条件查询查询)")
    @PostMapping("/{page}/{limit}")
    public Result getTeacherList(@PathVariable(value = "page") Integer page, @PathVariable(value = "limit") Integer limit, @RequestBody TeacherQuery teacherQuery) {

        return teacherService.getTeacherListBypage(page, limit, teacherQuery);
    }

    @ApiOperation(value = "新增老师列表")
    @PostMapping("/save")
    public Result addTeacher(@RequestBody Teacher teacher) {
        if (teacherService.save(teacher)) {
            return Result.ok();
        }
        return Result.error();
    }

    @ApiOperation("根据 id 查询老师")
    @GetMapping("{id}")
    public Result getTeacherById(@PathVariable String id) {

        return teacherService.getTeacherById(id);
    }

    @ApiOperation("根据 id 修改老师")
    @PutMapping("/update")
    public Result updateTeacherById(@RequestBody Teacher teacher) {
        if (teacherService.updateById(teacher)) {
            return Result.ok();
        }
        return Result.error();
    }

}
