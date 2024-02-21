package com.imooc.spring.ioc;

import com.sun.javafx.binding.StringFormatter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.SimpleDateFormat;

@Configuration  //说明当前类是配置类
@ComponentScan(value = "com.imooc")
public class Config {
    @Bean
    public SimpleDateFormat simpleDateFormat() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat;
    }
}
