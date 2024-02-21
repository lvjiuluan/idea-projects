package com.imooc.spring.ioc;

import com.imooc.spring.ioc.entity.Apple;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringApplication {
    public static void main(String[] args) {
        /*
        * 初始化IoC容器、并实例化对象
        * 在这个容器中有一个名为apple1的对象
        * */
        ApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:applicationContext.xml",
                        "classpath:applicationContext2.xml");
        Apple apple = (Apple) context.getBean("apple10");
        Apple apple2 = (Apple) context.getBean("apple11");
        System.out.println(apple.getTittle());
        System.out.println(apple2.getTittle());
    }
}
