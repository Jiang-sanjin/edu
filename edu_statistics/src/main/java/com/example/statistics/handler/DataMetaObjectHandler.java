package com.example.statistics.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 在操作数据库的时候根据语句拦截，帮我们自动补充被拦截的语句的属性
 */
@Component
public class DataMetaObjectHandler implements MetaObjectHandler{

    @Override
    public void insertFill(MetaObject metaObject) {
        //自动补充teacher对象属性中的数据， isDeleted ： Boolean 所以我们应该放入true, false

        this.setFieldValByName("is_deleted",false,metaObject);  //是否删除
        this.setFieldValByName("gmt_create",new Date(),metaObject);  //创建时间
        this.setFieldValByName("gmt_modified",new Date(),metaObject);  //更新时间

    }

    @Override
    public void updateFill(MetaObject metaObject) {


        this.setFieldValByName("gmt_modified",new Date(),metaObject);
    }
}
