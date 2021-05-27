package com.example.user.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author jiangsanjin
 * @since 2021-04-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("edu_user")
@ApiModel(value="User对象", description="用户表")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id")
    @TableId(value = "id",type = IdType.ID_WORKER)
    private String id;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "性别 1 女，2 男")
    private Integer sex;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "用户头像")
    private String avatar;

    @ApiModelProperty(value = "用户签名")
    private String sign;

    @ApiModelProperty(value = "是否禁用 1（true）已禁用，  0（false）未禁用")
    private Boolean is_disabled;

    @ApiModelProperty(value = "逻辑删除 1（true）已删除， 0（false）未删除")
    @TableLogic
    @TableField(fill = FieldFill.INSERT, value = "is_deleted")
    private Boolean is_deleted;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT,value = "gmt_create")
    private Date gmt_create;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE,value = "gmt_modified")
    private Date gmt_modified;


}
