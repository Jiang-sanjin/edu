package com.example.teacher.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.teacher.pojo.Video;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author jiangsanjin
 * @since 2021-03-27
 */
public interface VideoService extends IService<Video> {

    /**
     * 删除 课程视频（小节）
     * @param id
     * @return
     */
    Boolean removeVideoById(String id);

    /**
     * 根据课程id 删除小节
     * @param id
     */
    Boolean removeVideoByCourseId(String id);
}
