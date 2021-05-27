package com.example.teacher.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 课程科目
 * </p>
 *
 * @author jiangsanjin
 * @since 2021-03-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("edu_subject")
@ApiModel(value="Subject对象", description="课程科目")
public class Subject implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "课程类别ID")
    @TableId(value = "id",type = IdType.ID_WORKER)
    private String id;

    @ApiModelProperty(value = "类别名称")
    private String title;

    @ApiModelProperty(value = "父ID")
    private String parent_id;

    @ApiModelProperty(value = "排序字段")
    private Integer sort;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT,value = "gmt_create")
    private Date gmt_create;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE,value = "gmt_modified")
    private Date gmt_modified;


}
