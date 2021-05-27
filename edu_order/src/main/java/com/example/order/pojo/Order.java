package com.example.order.pojo;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 订单
 * </p>
 *
 * @author jiangsanjin
 * @since 2021-04-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("edu_order")
@ApiModel(value="Order对象", description="订单")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    @ApiModelProperty(value = "订单号")
    private String order_no;

    @ApiModelProperty(value = "课程id")
    private String course_id;

    @ApiModelProperty(value = "课程名称")
    private String course_title;

    @ApiModelProperty(value = "课程封面")
    private String course_cover;

    @ApiModelProperty(value = "讲师名称")
    private String teacher_name;

    @ApiModelProperty(value = "会员id")
    private String member_id;

    @ApiModelProperty(value = "会员昵称")
    private String nickname;

    @ApiModelProperty(value = "会员手机")
    private String mobile;

    @ApiModelProperty(value = "订单金额（分）")
    private BigDecimal total_fee;

    @ApiModelProperty(value = "支付类型（1：微信 2：支付宝）")
    private Integer pay_type;

    @ApiModelProperty(value = "订单状态（0：未支付 1：已支付）")
    private Integer status;

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
