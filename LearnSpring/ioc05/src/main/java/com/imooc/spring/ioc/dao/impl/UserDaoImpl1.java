package com.imooc.spring.ioc.dao.impl;

import com.imooc.spring.ioc.dao.IUserDao;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl1 implements IUserDao {
    public UserDaoImpl1() {
        System.out.println("创建UserDaoImpl1对象...");
    }

    @Override
    public void addUser() {
        System.out.println("UserDaoImpl1执行addUser方法");
    }
}
