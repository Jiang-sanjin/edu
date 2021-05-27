package com.example.user.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ChangeVo {
    @ApiModelProperty(value = "邮箱号")
    private String email;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "验证码")
    private String code;
}
