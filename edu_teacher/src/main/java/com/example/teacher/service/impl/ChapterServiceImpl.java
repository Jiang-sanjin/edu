package com.example.teacher.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.teacher.mapper.ChapterMapper;
import com.example.teacher.pojo.Chapter;
import com.example.teacher.pojo.Video;
import com.example.teacher.pojo.vo.OneChapter;
import com.example.teacher.pojo.vo.TwoVideo;
import com.example.teacher.service.ChapterService;
import com.example.teacher.service.VideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author jiangsanjin
 * @since 2021-03-26
 */
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {

    @Autowired
    private VideoService videoService;

    @Autowired
    private ChapterMapper chapterMapper;

    /**
     * 根据课程的ID获取章节和小节列表
     * @param courseId
     * @return
     */
    @Override
    public List<OneChapter> getChapterAndVideoById(String courseId) {
        List<OneChapter> list = new ArrayList<>();
        //判断ID
        //1、根据ID查询章节列表
        QueryWrapper<Chapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        wrapper.orderByAsc("sort");  //升序排序
        List<Chapter> chapterList = chapterMapper.selectList(wrapper);
        //判断集合
        //2、遍历章节列表
        for(Chapter chapter : chapterList){

            //3、把每一个章节对象复制到OneChapter
            OneChapter oneChapter = new OneChapter();
            BeanUtils.copyProperties(chapter, oneChapter);
            //4、根据每一个章节ID查询小节列表
            QueryWrapper<Video> wr = new QueryWrapper<>();
            wr.eq("chapter_id",oneChapter.getId());
            wr.orderByAsc("sort");
            List<Video> videoList = videoService.list(wr);
            //5、遍历每一个小节
            for(Video video : videoList){
                //6、把每一个小节对象复制到TwoVideo
                TwoVideo twoVideo = new TwoVideo();
                BeanUtils.copyProperties(video, twoVideo);
                //7、把每一个TwoVideo加到章节children
                oneChapter.getChildren().add(twoVideo);
            }

            //8、把每一个章节加到总集合中
            list.add(oneChapter);
        }


        return list;
    }

    /**
     * 保存课程章节  的时候  需要判断标题是否存在，避免重复存储
     * @param chapter
     * @return
     */
    @Override
    public boolean saveChapter(Chapter chapter) {

        if(chapter == null){
            return false;
        }
        QueryWrapper<Chapter> wrapper = new QueryWrapper<>();
        wrapper.eq("title", chapter.getTitle());
        Integer count = chapterMapper.selectCount(wrapper);
        if(count > 0){
            return false;
        }
        int insert = chapterMapper.insert(chapter);

        return insert == 1;
    }

    /**
     * 修改章节
     * 修改时判断章节名称是否存在
     * @param chapter
     * @return
     */
    @Override
    public boolean updateChapterById(Chapter chapter) {
        if(chapter == null){
            return false;
        }
        QueryWrapper<Chapter> wrapper = new QueryWrapper<>();
        wrapper.eq("title", chapter.getTitle());
        Integer count = chapterMapper.selectCount(wrapper);
        if(count > 0){
            return false;
        }
        int insert = chapterMapper.updateById(chapter);

        return insert == 1;
    }

    /**
     * 删除课程章节
     * @param id
     * @return
     */
    @Override
    public Boolean removeChapterById(String id) {
        //1、判断章节的ID下面是否存在小节
        QueryWrapper<Video> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id", id);
        List<Video> list = videoService.list(wrapper);
        if(list.size() != 0){
            return false;
        }
        //2、如果有不能删除直接false
        int i = chapterMapper.deleteById(id);
        //3、删除章节
        return i == 1;
    }


}
