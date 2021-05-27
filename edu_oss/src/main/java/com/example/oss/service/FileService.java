package com.example.oss.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    /**
     * 上传图片
     * @param file
     * @return 图片地址
     */
    String upload(MultipartFile file);

}
