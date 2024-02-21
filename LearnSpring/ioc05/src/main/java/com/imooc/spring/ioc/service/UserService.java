package com.imooc.spring.ioc.service;

import com.imooc.spring.ioc.dao.IUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    @Qualifier("userDaoImpl1")
    private IUserDao userDao;

    public void register() {
        userDao.addUser();
    }

  /*  public IUserDao getUserDao() {
        return userDao;
    }*/
   /* @Autowired
    @Qualifier("userDaoImpl1")
    public void setUserDao(IUserDao userDao) {
        System.out.println("Service调用setUserDao方法");
        this.userDao = userDao;
    }*/

    /*@Autowired
    @Qualifier("userDaoImpl1")
    public void add(IUserDao userDao) {
        System.out.println("Service调用add");
    }*/
}
