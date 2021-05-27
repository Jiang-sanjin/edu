package com.example.teacher.controller;


import com.example.teacher.pojo.Video;
import com.example.teacher.result.Result;
import com.example.teacher.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author jiangsanjin
 * @since 2021-03-27
 */
@RestController
@RequestMapping("/video")
@CrossOrigin
public class VideoController {

    @Autowired
    private VideoService videoService;

    @ApiOperation(value = "保存课程视频（小节）")
    @PostMapping("save")
    public Result save(@RequestBody Video video){
        System.out.println(video);
        boolean save = videoService.save(video);
        if(save){
            return Result.ok();
        } else{
            return Result.error();
        }
    }


    @ApiOperation(value = "根据ID查询Video对象的 回显")
    @GetMapping("{id}")
    public Result getVideoById(@PathVariable String id){
        Video video = videoService.getById(id);
        return Result.ok().data("video", video);
    }

    @ApiOperation(value = "修改 课程视频（小节）")
    @PutMapping("update")
    public Result update(@RequestBody Video video){
        boolean update = videoService.updateById(video);
        if(update){
            return Result.ok();
        } else{
            return Result.error();
        }
    }

    @ApiOperation("删除 课程视频（小节）")
    @DeleteMapping("{id}")
    public Result deleteById(@PathVariable String id){
        Boolean flag = videoService.removeVideoById(id);
        if(flag){
            return Result.ok();
        } else{
            return Result.error();
        }
    }
}
