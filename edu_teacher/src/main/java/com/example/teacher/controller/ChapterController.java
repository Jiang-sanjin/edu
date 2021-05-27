package com.example.teacher.controller;


import com.example.teacher.pojo.Chapter;
import com.example.teacher.pojo.vo.CourseVo;
import com.example.teacher.pojo.vo.OneChapter;
import com.example.teacher.result.Result;
import com.example.teacher.service.ChapterService;
import com.example.teacher.service.CourseService;
import com.example.teacher.service.impl.CourseServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 章节
 * 前端控制器
 * </p>
 *
 * @author jiangsanjin
 * @since 2021-03-26
 */
@RestController
@RequestMapping("/chapter")
@CrossOrigin
public class ChapterController {

    @Autowired
    private ChapterService chapterService;



    /**
     * 根据课程的ID获取章节和小节列表
     * Tree
     * 1、创建一个对象，作为章节Vo，里面包括二级集合
     * 2、创建二级的Vo （video）
     * 3、根据课程ID查询章节的列表，遍历这个列表，根据每一个章节的ID查询二级列表（video集合）
     */
    @ApiOperation(value = "根据课程的ID获取章节和小节列表")
    @GetMapping("{courseId}")
    public Result getChapterAndVideoById(@PathVariable String courseId){
        List<OneChapter> list = chapterService.getChapterAndVideoById(courseId);
        return Result.ok().data("list",list);
    }

    @ApiOperation(value = "保存课程章节")
    @PostMapping("save")
    public Result save(@RequestBody Chapter chapter){
        boolean save = chapterService.saveChapter(chapter);
        if(save){
            return Result.ok();
        } else{
            return Result.error();
        }
    }


    @ApiOperation(value = "根据id 单个查询chapter")
    @GetMapping("get/{id}")
    public Result getChapterById(@PathVariable String id){
        Chapter chapter = chapterService.getById(id);
        return Result.ok().data("chapter",chapter);
    }

    @ApiOperation(value = "更新课程章节")
    @PutMapping("update")
    public Result updateById(@RequestBody Chapter chapter){
        boolean b = chapterService.updateChapterById(chapter);
        if(b){
            return Result.ok();
        } else{
            return Result.error();
        }
    }

    @ApiOperation(value = "删除课程章节")
    @DeleteMapping("{id}")
    public Result deleteById(@PathVariable String id){
        Boolean flag = chapterService.removeChapterById(id);
        if(flag){
            return Result.ok();
        } else{
            return Result.error();
        }
    }
}
