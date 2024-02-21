package com.imooc.spring.aop.config;

import com.imooc.spring.aop.aspect.MethodAspect;
import com.imooc.spring.aop.aspect.MethodAspectAnnotation;
import com.imooc.spring.aop.dao.EmployeeDao;
import com.imooc.spring.aop.dao.UserDao;
import com.imooc.spring.aop.service.EmployeeService;
import com.imooc.spring.aop.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;


@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = "com.imooc")
public class ApplicationContextConfig {
    @Bean
    public UserDao userDao() {
        return new UserDao();
    }

    @Bean
    public EmployeeDao employeeDao() {
        return new EmployeeDao();
    }

    @Bean
    public UserService userService(UserDao userDao) {
        UserService userService = new UserService();
        userService.setUserDao(userDao);
        return userService;
    }

    @Bean
    public EmployeeService employeeService(EmployeeDao employeeDao) {
        EmployeeService employeeService = new EmployeeService();
        employeeService.setEmployeeDao(employeeDao);
        return employeeService;
    }


}