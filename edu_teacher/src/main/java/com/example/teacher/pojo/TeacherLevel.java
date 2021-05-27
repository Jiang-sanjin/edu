package com.example.teacher.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author jiangsanjin
 * @since 2021-03-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("edu_teacher_level")
@ApiModel(value="TeacherLevel对象", description="")
public class TeacherLevel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "讲师头衔id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "讲师头衔")
    private String name;


}
