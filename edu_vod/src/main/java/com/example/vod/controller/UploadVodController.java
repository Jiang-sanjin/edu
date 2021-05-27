package com.example.vod.controller;


import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.example.vod.result.Result;
import com.example.vod.service.VodService;
import com.example.vod.util.AliyunVodSDKUtil;
import com.example.vod.util.ConstantPropertiesUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/vod")
@CrossOrigin
public class UploadVodController {

    @Autowired
    private VodService vodService;

    @ApiOperation(value = "上传文件")
    @PostMapping("upload")
    public Result upload(MultipartFile file){
        String videoSourceId = vodService.upload(file);
        return Result.ok().data("videoSourceId", videoSourceId);
    }


    @ApiOperation(value = "根据视频ID删除视频")
    @DeleteMapping("{videoSourceId}")
    public Result deleteVodById(@PathVariable String videoSourceId){
        Boolean flag = vodService.deleteVodById(videoSourceId);
        if(flag){
            return Result.ok();
        }
        return Result.error();
    }


    @ApiOperation(value = "根据多个视频ID删除视频")
    @DeleteMapping("removeList")
    public Result getRemoveList(@RequestParam("videoIdList") List<String> videoIdList){

        Boolean flag = vodService.getRemoveListByIds(videoIdList);
        if(flag){
            return Result.ok();
        }
        return Result.error();
    }

    @ApiOperation(value = "根据视频id获取视频凭证")
    @GetMapping("getPlayAuth/{id}")
    public Result getPlayAuth(@PathVariable String id){
        try{
            //创建初始化对象
            DefaultAcsClient client =
                    AliyunVodSDKUtil.initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET);
            //创建获取凭证的request对象和response对象
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();

            //向request设置视频Id
            request.setVideoId(id);

            //调用方法 得到凭证
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            String playAuth = response.getPlayAuth();

            return Result.ok().data("playAuth",playAuth);
        }catch (Exception e){
            e.printStackTrace();
        }
        return Result.error();
    }


}
