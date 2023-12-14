package com.lagou.service.impl;

import com.lagou.dao.AccountDao;
import com.lagou.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountDao accountDao;

    @Override
    //开启事务控制 (对当前类的所有方法)
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ, timeout = -1, readOnly = false)
    public void transfer(String outUser, String inUser, double money) {
        accountDao.out(outUser, money);
//        int i = 1 / 0;
        accountDao.in(inUser, money);
    }
}
