package com.example.teacher.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.teacher.pojo.Subject;
import com.example.teacher.pojo.vo.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author jiangsanjin
 * @since 2021-03-26
 */
public interface SubjectService extends IService<Subject> {

    /**
     * 导入excel文件
     * @param file
     * @return
     */
    List<String> importEXCL(MultipartFile file);

    /**
     * 获取课程分类的Tree
     * @return
     */
    List<OneSubject> getTree();

    /**
     * 根据id删除课程
     * @param id
     * @return
     */
    boolean deleteById(String id);

    /**
     * 新增一级分类
     * @param subject
     * @return
     */
    boolean saveLevelOne(Subject subject);

    /**
     * 新增二级分类
     * @param subject
     * @return
     */
    boolean saveLevelTwo(Subject subject);
}
