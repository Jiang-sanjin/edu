package com.example.order.client;


import com.example.order.pojo.vo.CourseWebVo;
import com.example.order.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;



@Component
@FeignClient("edu-teacher")
public interface TeacherClient {

    /**
     * 根据课程ID查询课程
     * @param courseId
     * @return
     */
    @PostMapping(value = "/course/getCourseInfoOrder/{courseId}")
    public CourseWebVo getCourseWebVoById(@PathVariable("courseId") String courseId);

    //根据课程id更改销售量
    @GetMapping("/course/updateBuyCount/{id}")
    public Result updateBuyCountById(@PathVariable("id") String id);

}
