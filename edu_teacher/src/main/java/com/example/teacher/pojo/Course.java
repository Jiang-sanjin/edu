package com.example.teacher.pojo;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 课程
 * </p>
 *
 * @author jiangsanjin
 * @since 2021-03-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("edu_course")
@ApiModel(value="Course对象", description="课程")
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "课程ID")
    @TableId(value = "id",type = IdType.ID_WORKER)
    private String id;

    @ApiModelProperty(value = "课程讲师ID")
    private String teacher_id;


    @ApiModelProperty(value = "课程专业一级分类ID")
    private String subject_parent_id;

    @ApiModelProperty(value = "课程专业ID")
    private String subject_id;



    @ApiModelProperty(value = "课程标题")
    private String title;

    @ApiModelProperty(value = "课程销售价格，设置为0则可免费观看")
    private BigDecimal price;

    @ApiModelProperty(value = "总课时")
    private Integer lesson_num;

    @ApiModelProperty(value = "课程封面图片路径")
    private String cover;

    @ApiModelProperty(value = "销售数量")
    private Long buy_count;

    @ApiModelProperty(value = "浏览数量")
    private Long view_count;

    @ApiModelProperty(value = "乐观锁")
    private Long version;

    @ApiModelProperty(value = "视频状态 Draft未发布  Normal已发布")
    private String status;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT,value = "gmt_create")
    private Date gmt_create;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE,value = "gmt_modified")
    private Date gmt_modified;


}
