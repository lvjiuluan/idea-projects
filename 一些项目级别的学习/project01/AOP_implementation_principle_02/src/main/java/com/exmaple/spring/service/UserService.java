package com.exmaple.spring.service;

import com.exmaple.spring.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public void add() {
        userDao.insert();
    }

    public void remove() {
        userDao.delete();
    }

}
