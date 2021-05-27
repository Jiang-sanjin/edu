package com.example.teacher.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.teacher.client.VodClient;
import com.example.teacher.mapper.VideoMapper;
import com.example.teacher.pojo.Video;
import com.example.teacher.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.xml.ws.Action;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author jiangsanjin
 * @since 2021-03-27
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {

    @Autowired
    private VodClient vodClient;

    @Autowired
    private VideoMapper videoMapper;
    /**
     * 删除 课程视频（小节）
     *
     * @param id
     * @return
     */
    @Override
    public Boolean removeVideoById(String id) {

        // 删除阿里云上的视频
        //查询云端视频id
        Video video = videoMapper.selectById(id);
        String videoSourceId = video.getVideo_source_id();
        System.out.println("id:   "+videoSourceId);
        //删除视频资源
        if(!StringUtils.isEmpty(videoSourceId)){
            vodClient.removeVideo(videoSourceId);
        }

        // 删除数据库中的Video
        int delete = videoMapper.deleteById(id);

        return delete == 1;
    }

    @Override
    public Boolean removeVideoByCourseId(String courseId) {
        QueryWrapper<Video> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        wrapper.select("video_source_id");
        List<Video> videoList = videoMapper.selectList(wrapper);
        //定义一个集合存放视频ID
        List<String> videoIds = new ArrayList<>();
        //2、可以获取视频ID
        for(Video video : videoList){
            if(!StringUtils.isEmpty(video.getVideo_source_id())){
                videoIds.add(video.getVideo_source_id());
            }
        }
        if(videoIds.size() > 0){
            vodClient.getRemoveList(videoIds);
        }

        QueryWrapper<Video> wr = new QueryWrapper<>();
        wr.eq("course_id",courseId);
        //3、删除
        int delete = videoMapper.delete(wr);

        return delete > 0;
    }
}
