package com.example.teacher.pojo.vo;


import com.example.teacher.pojo.Course;
import com.example.teacher.pojo.CourseDescription;
import lombok.Data;

/**
 * 接收课程对象和描述对象   用于业务层之间的数据传递
 */
@Data
public class CourseVo {

    /**
     * 课程对象
     */
    private Course eduCourse;

    /**
     * 课程简介对象
     */
    private CourseDescription courseDescription;

}
