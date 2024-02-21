package com.imooc.spring.ioc.context;


import com.imooc.spring.ioc.entity.Apple;
import org.junit.Test;

public class ClassPathXmlApplicationContextTest {

    @Test
    public void testGetBean() {
        ApplicationContext context = new ClassPathXmlApplicationContext();
        Apple apple = (Apple) context.getBean("sweetApple");
        System.out.println(apple);
    }
}