package com.example.spring;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

public class SpringApplication {
    public static void main(String[] args) {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        System.out.println("使用ApplicationContext加载配置文件完毕");
        BeanFactory beanFactory = new XmlBeanFactory(new ClassPathResource("applicationContext.xml"));
//        context.getBean("orderService");
        Object orderService = beanFactory.getBean("orderService");
    }
}
/*
OrderServiceImpl构造方法被调用了com.example.spring.service.impl.OrderServiceImpl@1677d1
EmailServiceImpl构造方法被调用了com.example.spring.service.impl.EmailServiceImpl@3e92efc3
OrderProcessor构造方法被调用了com.example.spring.processor.OrderProcessor@1622f1b
*
* */