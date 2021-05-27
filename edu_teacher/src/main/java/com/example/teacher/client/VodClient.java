package com.example.teacher.client;


import com.example.teacher.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("edu-vod")
@Component
public interface VodClient {

    /**
     * 调用者使用被调用者的api
     *
     */

    //删除视频
    @DeleteMapping(value = "/vod/{videoSourceId}")
    public Result removeVideo(@PathVariable("videoSourceId") String videoSourceId);

    @DeleteMapping(value = "/vod/removeList")
    public Result getRemoveList(@RequestParam("videoIdList") List<String> videoIdList);


}

