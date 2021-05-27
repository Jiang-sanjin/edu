package com.example.teacher.converter;


import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 日期转换   继承Converter
 *
 * @author
 */

@Component
public class DateConverter implements Converter<String, Date> {

    private String datePattern = "yyyy-MM-dd HH:mm:ss";
    @Override
    public Date convert(String source) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern);
        try {
            return simpleDateFormat.parse(source);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // @Override
    // public LocalDate convert(String s) {
    //     try {
    //         return LocalDate.parse(s, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    //     return null;
    // }


}
