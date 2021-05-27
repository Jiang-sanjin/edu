package com.example.oss.controller;


import com.example.oss.result.Result;
import com.example.oss.service.FileService;
import com.example.oss.util.ConstantPropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("oss")
@CrossOrigin   //跨域
public class FileController {

    @Autowired
    private FileService fileService;

    /**
     * 上传文件
     * 接收请求
     * 返回响应
     */
    @PostMapping("file/upload")
    public Result upload( @RequestParam("file") MultipartFile file,@RequestParam(value = "host", required = false) String host){
        // 参数host  文件上传路径
        if(!StringUtils.isEmpty(host)){ // cover
            ConstantPropertiesUtil.FILE_HOST = host;
        }
        String uploadUrl = fileService.upload(file);
        //返回r对象
        return Result.ok().message("文件上传成功").data("url", uploadUrl);

    }

}
