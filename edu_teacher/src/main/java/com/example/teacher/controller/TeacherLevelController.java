package com.example.teacher.controller;


import com.example.teacher.pojo.TeacherLevel;
import com.example.teacher.result.Result;
import com.example.teacher.service.TeacherLevelService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *     职称
 *  前端控制器
 * </p>
 *
 * @author jiangsanjin
 * @since 2021-03-24
 */
@RestController
@RequestMapping("/teacher-level")
@CrossOrigin
public class TeacherLevelController {

    @Autowired
    private TeacherLevelService teacherLevelService;

    @ApiOperation(value = "获取所有的职称")
    @GetMapping("/")
    public Result getTeacherLevel(){
        List<TeacherLevel> list = teacherLevelService.list();
        if ( null!= list){
            return Result.ok().data("teacherLevel",list);
        }
        return Result.error();
    }

    @ApiOperation(value = "添加职称")
    @PostMapping("/add")
    public Result addLevel(@RequestBody TeacherLevel teacherLevel){

        if (teacherLevelService.save(teacherLevel)){
            return Result.ok();
        }
        return Result.error();
    }

    @ApiOperation(value = "更新职称")
    @PutMapping("/update")
    public Result updateLevel(@RequestBody TeacherLevel teacherLevel){
        if (teacherLevelService.updateById(teacherLevel)){
            return Result.ok();
        }
        return Result.error();
    }

    @ApiOperation(value = "删除职称")
    @DeleteMapping("/delete/{id}")
    public Result deltet(@PathVariable String id){
        if (teacherLevelService.removeById(id)){
            return Result.ok();
        }
        return Result.error();
    }

}
