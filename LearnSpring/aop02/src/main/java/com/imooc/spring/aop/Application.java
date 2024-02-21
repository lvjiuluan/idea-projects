package com.imooc.spring.aop;

import com.imooc.spring.aop.service.*;

import java.lang.reflect.Proxy;

public class Application {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        ProxyInvocationHandler invocationHandler = new ProxyInvocationHandler(userService);
        /*
         * 动态创建代理类
         * */
        System.out.println(userService.getClass() == UserService.class);
        System.out.println(userService.getClass() == UserServiceImpl.class);
        UserService userServiceProxy = (UserService)
                (Proxy.newProxyInstance(UserServiceImpl.class.getClassLoader(),
                        UserServiceImpl.class.getInterfaces(),
                        invocationHandler));
        userServiceProxy.createUser();

        /*
        * Proxy.newProxyInstance, 根据已有的接口,生成代理类
        * */
        EmployeeService employeeService = new EmployeeServiceImpl();
        invocationHandler.setTarget(employeeService);
        EmployeeService employeeServiceProxy = (EmployeeService)
                Proxy.newProxyInstance(employeeService.getClass().getClassLoader(),
                        employeeService.getClass().getInterfaces(),
                        invocationHandler);
        employeeServiceProxy.createEmployee();

    }
}
