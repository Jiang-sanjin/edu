package com.example.statistics.service;

import com.example.statistics.pojo.Statistics;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author jiangsanjin
 * @since 2021-04-01
 */
public interface StatisticsService extends IService<Statistics> {

    /**
     * 获取统计数据，添加统计分析表里面
     * @param day
     */
    void getCreateData(String day);

    /**
     * 返回进行统计的数据，两个数组
     * @param begin
     * @param end

     * @return
     */
    Map<String, Object> getCountData(String begin, String end);
}
