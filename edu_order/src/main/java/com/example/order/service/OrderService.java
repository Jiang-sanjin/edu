package com.example.order.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.order.pojo.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.order.result.Result;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author jiangsanjin
 * @since 2021-04-16
 */
public interface OrderService extends IService<Order> {

    /**
     * 根据订单号查询订单信息
     * @param courseId
     * @param id
     * @return
     */
    String createOrder(String courseId, String id);

    /**
     * 查询用户所有购买的课程
     *
     * @param objectPage
     * @param memberId
     * @return
     */
    void getUserCourse(Page<Order> objectPage, String memberId);


}
