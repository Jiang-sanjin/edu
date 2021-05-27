package com.example.statistics.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 网站统计日数据
 * </p>
 *
 * @author jiangsanjin
 * @since 2021-04-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("edu_statistics")
@ApiModel(value="Statistics对象", description="网站统计日数据")
public class Statistics implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id",type = IdType.ID_WORKER)
    private String id;

    @ApiModelProperty(value = "统计日期")
    private String date_calculated;

    @ApiModelProperty(value = "注册人数")
    private Integer register_num;

    @ApiModelProperty(value = "登录人数")
    private Integer login_num;

    @ApiModelProperty(value = "每日播放视频数")
    private Integer video_view_num;

    @ApiModelProperty(value = "每日新增课程数")
    private Integer course_num;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT,value = "gmt_create")
    private Date gmt_create;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE,value = "gmt_modified")
    private Date gmt_modified;


}
