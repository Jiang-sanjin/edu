package com.example.statistics.schdule;

import com.example.statistics.service.StatisticsService;
import com.example.statistics.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ScheduledTask {
    @Autowired
    private StatisticsService statisticsService;

    //cron表达式  设置时间 spring写6位，不然会报错
    // 在线生成cron表达式  http://cron.qqe2.com/
    //在每天0点，把前一天数据进行数据查询添加
    @Scheduled(cron = "0 0 0 * * ? ")
    public void taskDaily() {
        statisticsService.getCreateData(DateUtil.formatDate(DateUtil.addDays(new Date(), -1)));
    }
}
