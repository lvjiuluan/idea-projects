package com.imooc.spring.ioc.service;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
@Service
@Scope("singleton")
public class BookService {
    @PostConstruct
    public void init(){
        System.out.println("初始化BookService对象");
    }
    @PreDestroy
    public void destroy(){
        System.out.println("调用destroy方法");
    }
}
