package com.example.teacher.pojo.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 *  接收 一级分类
 *  用于业务层之间的数据传递
 */
@Data
public class OneSubject {

    private String id;

    private String title;

    List<TwoSubject> children = new ArrayList<>();

}
