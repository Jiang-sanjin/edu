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
 * 支付日志表
 * </p>
 *
 * @author jiangsanjin
 * @since 2021-04-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("edu_pay_log")
@ApiModel(value="PayLog对象", description="支付日志表")
public class PayLog implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    @ApiModelProperty(value = "订单号")
    private String order_no;

    @ApiModelProperty(value = "支付完成时间")
    private Date pay_time;

    @ApiModelProperty(value = "支付金额（分）")
    private BigDecimal total_fee;

    @ApiModelProperty(value = "交易流水号")
    private String transaction_id;

    @ApiModelProperty(value = "交易状态")
    private String trade_state;

    @ApiModelProperty(value = "支付类型（1：微信 2：支付宝）")
    private Integer pay_type;

    @ApiModelProperty(value = "其他属性")
    private String attr;

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
