package com.example.teacher.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 课程视频
 * </p>
 *
 * @author jiangsanjin
 * @since 2021-03-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("edu_video")
@ApiModel(value="Video对象", description="课程视频")
public class Video implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "视频ID")
    private String id;

    @ApiModelProperty(value = "课程ID")
    private String course_id;

    @ApiModelProperty(value = "章节ID")
    private String chapter_id;

    @ApiModelProperty(value = "节点名称")
    private String title;

    @ApiModelProperty(value = "排序字段")
    private Integer sort;

    @ApiModelProperty(value = "播放次数")
    private Long play_count;

    @ApiModelProperty(value = "是否可以试听：0免费 1收费")
    private Boolean is_free;

    @ApiModelProperty(value = "视频资源")
    private String video_source_id;

    @ApiModelProperty(value = "视频时长（秒）")
    private Float duration;

    @ApiModelProperty(value = "视频状态:见阿里云文档")
    private String status;

    @ApiModelProperty(value = "视频源文件大小（字节）")
    private Long size;

    @ApiModelProperty(value = "乐观锁")
    private Long version;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmt_create;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmt_modified;

    @ApiModelProperty(value = "云服务器上存储的视频文件名称")
    private String video_original_name;


}
