package com.imooc.spring.aop.service;

public class EmployeeServiceImpl implements EmployeeService {
    @Override
    public void createEmployee() {
        System.out.println("执行创建员工逻辑");
    }
}
