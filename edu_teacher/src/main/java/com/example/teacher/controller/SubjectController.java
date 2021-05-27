package com.example.teacher.controller;


import com.example.teacher.pojo.Subject;
import com.example.teacher.pojo.vo.OneSubject;
import com.example.teacher.result.Result;
import com.example.teacher.service.SubjectService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author jiangsanjin
 * @since 2021-03-26
 */
@RestController
@RequestMapping("/subject")
@CrossOrigin
public class SubjectController {

    @Autowired
    private SubjectService subjectService;


    @ApiOperation(value = "excel文件导入")
    @PostMapping("/import")
    public Result importSubject(MultipartFile file) {
        // 因为考虑到EXCL模板中数据不准确所以返回多个错误信息，那么多个错误信息放在集合中
        List<String> messageList = subjectService.importEXCL(file);
        if (messageList.size() == 0) {  //没有报错信息显示成功
            return Result.ok();
        } else {
            return Result.error().data("messageList", messageList);
        }
    }

    @ApiOperation(value = "获取所有课程分类")
    @GetMapping("/")
    public Result getSbuject() {
        List<Subject> subjectList = subjectService.list();
        if (null != subjectList) {
            return Result.ok().data("subject", subjectList);
        }
        return Result.error();
    }


    @ApiOperation(value = "获取课程分类的Tree")
    @GetMapping("tree")
    public Result TreeSubject() {
        List<OneSubject> subjectList = subjectService.getTree();
        return Result.ok().data("subjectList", subjectList);
    }

    @ApiOperation(value = "根据id删除课程")
    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable String id) {

        if (subjectService.deleteById(id)) {
            return Result.ok();
        } else {
            return Result.error();
        }

    }

    @ApiOperation(value = "新增一级分类")
    @PostMapping("saveLevelOne")
    public Result saveLevelOne(
            @RequestBody Subject subject) {

        boolean result = subjectService.saveLevelOne(subject);
        if (result) {
            return Result.ok();
        } else {
            return Result.error().message("删除失败");
        }
    }

    @ApiOperation(value = "新增二级分类")
    @PostMapping("saveLevelTwo")
    public Result saveLevelTwo(
            @RequestBody Subject subject) {

        boolean result = subjectService.saveLevelTwo(subject);
        if (result) {
            return Result.ok();
        } else {
            return Result.error().message("保存失败");
        }
    }


}
