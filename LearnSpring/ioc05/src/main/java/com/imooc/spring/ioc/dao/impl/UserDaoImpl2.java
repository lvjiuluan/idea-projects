package com.imooc.spring.ioc.dao.impl;

import com.imooc.spring.ioc.dao.IUserDao;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl2 implements IUserDao {
    @Override
    public void addUser() {
        System.out.println("UserDaoImpl2执行addUser方法");
    }
}
