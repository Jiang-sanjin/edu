package com.example.statistics.controller;


import com.example.statistics.result.Result;
import com.example.statistics.service.StatisticsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author jiangsanjin
 * @since 2021-04-01
 */
@CrossOrigin
@RestController
@RequestMapping("/statistics")
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;


    @ApiOperation(value = "获取某一天数据，把数据添加统计分析表")
    @PostMapping("createData/{day}")
    public Result createData(@PathVariable String day) {
        //获取统计数据，添加统计分析表里面
        statisticsService.getCreateData(day);
        return Result.ok();
    }


    @ApiOperation(value = "返回进行统计的数据，两个数组")
    @GetMapping("getCountData/{begin}/{end}")
    public Result getDataCount(@PathVariable String begin,
                          @PathVariable String end) {
        //返回数据包含两部分，使用map进行封装，返回
        Map<String,Object> map = statisticsService.getCountData(begin,end);
        return Result.ok().data(map);
    }
}
