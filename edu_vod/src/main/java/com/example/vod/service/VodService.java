package com.example.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VodService {


    /**
     * 上传文件
     * @param file
     * @return 视频ID
     */
    String upload(MultipartFile file);

    /**
     * 根据视频资源ID删除云端视频
     * @param videoSourceId
     * @return
     */
    Boolean deleteVodById(String videoSourceId);

    /**
     * 根据多个视频ID删除视频
     * @param videoIdList
     * @return
     */
    Boolean getRemoveListByIds(List<String> videoIdList);
}
