package com.example.teacher.pojo.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class CoursePublishVo implements Serializable {

    private String id;//课程ID

    private String title;//课程名称

    private String subject_parent_title;//一级类目

    private String subject_title;//二级类目

    private String lesson_num;//课时

    private String teacher_name;//讲师名称

    private String price;//课程价格

    private String cover;//封面
}

