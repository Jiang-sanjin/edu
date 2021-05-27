package com.example.order.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.order.pojo.Order;
import com.example.order.result.Result;
import com.example.order.service.OrderService;
import com.example.order.util.JwtUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author jiangsanjin
 * @since 2021-04-16
 */
@CrossOrigin
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;


    @ApiOperation(value = "根据课程id和用户id创建订单，返回订单id")
    @PostMapping("createOrder/{courseId}")
    public Result saveOrder(@PathVariable String courseId, HttpServletRequest request){
        // 通过request 获取用户id
        String id = JwtUtils.getMemberIdByJwtToken(request);
        //创建订单 返回订单号
        String orderNo = orderService.createOrder(courseId,id);
        return Result.ok().data("orderId",orderNo);
    }

    @ApiOperation(value = "根据订单号查询订单信息")
    @GetMapping("getOrderInfo/{orderId}")
    public Result getOrderInfo(@PathVariable String orderId){

        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderId);
        Order order = orderService.getOne(wrapper);

        return Result.ok().data("order",order);
    }


    @ApiOperation(value = "根据课程id 用户id 查看是否购买过")
    @GetMapping("isBuyCourse/{courseId}")
    public Result isBuyCourse(@PathVariable("courseId") String courseId, HttpServletRequest request){
        // 通过request 获取用户id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        wrapper.eq("member_id",memberId);
        wrapper.eq("status",1);
        int count = orderService.count(wrapper);
        return Result.ok().data("isBuy",count);
    }


    @ApiOperation(value = "查询用户所有购买的课程")
    @PostMapping("getUserCourse/{page}/{limit}")
    public Result getUserCourse(@PathVariable Integer page,
                                @PathVariable Integer limit,HttpServletRequest request){
        // 通过request 获取用户id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        Page<Order> objectPage = new Page<>(page, limit);
        orderService.getUserCourse(objectPage,memberId);
        return Result.ok()
                .data("rows", objectPage.getRecords())
                .data("total", objectPage.getTotal());
    }


}
