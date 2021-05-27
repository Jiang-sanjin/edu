package com.example.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.order.client.TeacherClient;
import com.example.order.client.UserClient;
import com.example.order.pojo.Order;
import com.example.order.mapper.OrderMapper;
import com.example.order.pojo.vo.CourseWebVo;
import com.example.order.pojo.vo.User;
import com.example.order.result.Result;
import com.example.order.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.order.util.OrderNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author jiangsanjin
 * @since 2021-04-16
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {


    @Autowired
    TeacherClient teacherClient;

    @Autowired
    UserClient userClient;

    @Autowired
    OrderMapper orderMapper;

    /**
     * 根据订单号查询订单信息
     * @param courseId
     * @param id
     * @return
     */
    @Override
    public String createOrder(String courseId, String id) {
        //通过远程调用根据用户id获取用户信息
        // id = "1381541829063802882";
        User user = userClient.getUserInfoOrder(id);

        //通过远程调用根据课程id获取课信息
        CourseWebVo courseWebVo = teacherClient.getCourseWebVoById(courseId);

        // 创建Order对象，向order对象里面设置需要数据
        Order order = new Order();
        order.setOrder_no(OrderNoUtil.getOrderNo());//订单号
        order.setCourse_id(courseId); //课程id
        order.setCourse_title(courseWebVo.getTitle());
        order.setCourse_cover(courseWebVo.getCover());
        order.setTeacher_name(courseWebVo.getTeacher_name());
        order.setTotal_fee(courseWebVo.getPrice());
        order.setMember_id(id);
        order.setMobile(user.getMobile());
        order.setNickname(user.getNickname());
        order.setStatus(0);  //订单状态（0：未支付 1：已支付）
        order.setPay_type(1);  //支付类型 ，支付宝1
        orderMapper.insert(order);

        //更新课程销量
        teacherClient.updateBuyCountById(order.getCourse_id());

        //返回订单号
        return order.getOrder_no();
    }

    /**
     * 查询用户所有购买的课程
     *
     * @param objectPage
     * @param memberId
     * @return
     */
    @Override
    public void getUserCourse(Page<Order> objectPage, String memberId) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("member_id",memberId).eq("status",1);

        orderMapper.selectPage(objectPage,wrapper);

    }




}
