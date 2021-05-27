package com.example.teacher.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.teacher.pojo.Chapter;
import com.example.teacher.pojo.vo.OneChapter;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author jiangsanjin
 * @since 2021-03-26
 */
public interface ChapterService extends IService<Chapter> {

    /**
     * 根据课程的ID获取章节和小节列表
     * @param courseId
     * @return
     */
    List<OneChapter> getChapterAndVideoById(String courseId);

    /**
     * 保存课程章节
     * @param chapter
     * @return
     */
    boolean saveChapter(Chapter chapter);

    /**
     * 修改章节
     * 修改时判断章节名称是否存在
     * @param chapter
     * @return
     */
    boolean updateChapterById(Chapter chapter);

    /**
     * 删除课程章节
     * @param id
     * @return
     */
    Boolean removeChapterById(String id);


}
