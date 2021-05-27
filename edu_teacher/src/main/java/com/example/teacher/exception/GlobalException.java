package com.example.teacher.exception;

import com.example.teacher.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;



/**
 * 全局异常处理
 *
 * @author
 */
@Slf4j
@RestControllerAdvice
public class GlobalException {


    //1、全局异常类型 Exception
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e){
        e.printStackTrace();

        return Result.error().message("出错了");
    }



}
