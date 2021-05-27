package com.example.teacher.pojo.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class CourseQuery implements Serializable{


    @ApiModelProperty(value = "课程专业ID  (二级菜单)")
    private String subject_id;

    @ApiModelProperty(value = "课程一级菜单 id")
    private String subject_parent_id;


    @ApiModelProperty(value = "课程标题")
    private String title;


    @ApiModelProperty(value = "教师 id")
    private String teacher_id;

    @ApiModelProperty(value = "销量排序")
    private String buy_count_sort;

    @ApiModelProperty(value = "最新时间排序")
    private String gmt_modified_sort;

    @ApiModelProperty(value = "价格排序")
    private String price_sort;
}
