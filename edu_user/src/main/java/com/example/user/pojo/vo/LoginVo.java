package com.example.user.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


@Data
public class LoginVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "密码")
    private String password;
}
