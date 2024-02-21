package com.imooc.spring.aop;

import com.imooc.spring.aop.config.ApplicationContextConfig;
import com.imooc.spring.aop.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringApplication {
    public static void main(String[] args) {
        /*ApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        UserService userService = context.getBean("userService", UserService.class);
        userService.createUser();
        for (String beanName : context.getBeanDefinitionNames()) {
            System.out.println(beanName);
        }*/
        ApplicationContext context =
                new AnnotationConfigApplicationContext(ApplicationContextConfig.class);
        UserService userService = context.getBean("userService", UserService.class);
        userService.createUser();
        /*for (String beanName : context.getBeanDefinitionNames()) {
            System.out.println(beanName);
        }*/
        userService.generateRandomPassword("MD5",16);
    }
}
