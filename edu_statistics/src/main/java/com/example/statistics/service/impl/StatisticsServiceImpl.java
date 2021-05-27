package com.example.statistics.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.statistics.client.userClient;
import com.example.statistics.pojo.Statistics;
import com.example.statistics.mapper.StatisticsMapper;
import com.example.statistics.result.Result;
import com.example.statistics.service.StatisticsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author jiangsanjin
 * @since 2021-04-01
 */
@Service
public class StatisticsServiceImpl extends ServiceImpl<StatisticsMapper, Statistics> implements StatisticsService {

    @Autowired
    private userClient userClient;

    @Autowired
    private StatisticsMapper statisticsMapper;

    /**
     * 获取统计数据，添加统计分析表里面
     * @param day
     */
    @Override
    public void getCreateData(String day) {
        Result result = userClient.getCountRegister(day);
        Integer register = (Integer) result.getData().get("countRegister");
        System.out.println("统计    "+register);

        //删除表里面相同日期数据，再进行添加
        QueryWrapper<Statistics> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated",day);
        List<Statistics> list = statisticsMapper.selectList(wrapper);
        if ( null != list){
            statisticsMapper.delete(wrapper);
        }


        //把统计数据添加表
        Statistics daily = new Statistics();
        daily.setDate_calculated(day);//统计日期
        daily.setRegister_num(register); //注册人数

        daily.setVideo_view_num(RandomUtils.nextInt( 200));  //每日播放视频数
        daily.setCourse_num(RandomUtils.nextInt( 200));  //每日新增课程数
        daily.setLogin_num(RandomUtils.nextInt(200));  //每日登录人数

        statisticsMapper.insert(daily);
    }

    /**
     * 返回进行统计的数据，两个数组
     * @param begin
     * @param end
     * @return
     */
    @Override
    public Map<String, Object> getCountData(String begin, String end) {
        //1 根据时间范围进行数据查询
        QueryWrapper<Statistics> wrapper = new QueryWrapper<>();

        List<Statistics> register_num_list = statisticsMapper.selectList(wrapper.between("date_calculated",begin,end).select("date_calculated","register_num"));
        List<Statistics> login_num_list = statisticsMapper.selectList(wrapper.between("date_calculated",begin,end).select("date_calculated","login_num"));
        List<Statistics> video_view_num_list = statisticsMapper.selectList(wrapper.between("date_calculated",begin,end).select("date_calculated","video_view_num"));
        List<Statistics> course_num_list = statisticsMapper.selectList(wrapper.between("date_calculated",begin,end).select("date_calculated","course_num"));

        //把数据构建成想要的结构，最终变成两个json数组形式
        //创建list集合
        //日期集合
        List<String> calculatedList = new ArrayList<>();
        //register_num 注册人数集合
        List<Integer> register_num = new ArrayList<>();
        //login_num 登录人数集合
        List<Integer> login_num = new ArrayList<>();
        //video_view_num 视频播放集合
        List<Integer> video_view_num = new ArrayList<>();
        //course_num 每日添加课程集合
        List<Integer> course_num = new ArrayList<>();

        //向两个list集合中封装数据
        //遍历查询集合
        for (int i = 0; i < register_num_list.size(); i++) {
            //集合每个对象
            Statistics sta = register_num_list.get(i);
            //封装日期集合数据
            String dateCalculated = sta.getDate_calculated();
            calculatedList.add(dateCalculated);
            //封装注册人数集合
            register_num.add(sta.getRegister_num());

            //集合每个对象
            Statistics sta2 = login_num_list.get(i);
            //封装登录人数集合
            login_num.add(sta2.getLogin_num());

            //集合每个对象
            Statistics sta3 = video_view_num_list.get(i);
            //封装视频播放集合
            video_view_num.add(sta3.getVideo_view_num());

            //集合每个对象
            Statistics sta4 = course_num_list.get(i);
            //封装每日添加课程集合
            course_num.add(sta4.getCourse_num());

        }

        //创建map集合，把封装之后list集合放到map集合，返回
        Map<String, Object> map = new HashMap<>();
        map.put("calculatedList", calculatedList);
        map.put("register_num", register_num);
        map.put("login_num", login_num);
        map.put("video_view_num", video_view_num);
        map.put("course_num", course_num);

        return map;
    }
}
