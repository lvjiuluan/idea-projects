package com.imooc.spring.ioc;

import com.imooc.spring.ioc.entity.Apple;
import com.imooc.spring.ioc.entity.Child;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringApplication {
    public static void main(String[] args) {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
//        Child andy = context.getBean("andy", Child.class);
//        andy.eat();
        String[] beanDefinitionNames = context.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println(beanDefinitionName);
            System.out.println(context.getBean(beanDefinitionName).getClass().getName());
        }
        Apple apple = context.getBean("com.imooc.spring.ioc.entity.Apple#0", Apple.class);
        System.out.println(apple.getTittle());
    }
}
