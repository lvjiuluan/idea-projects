package com.lagou.service.impl;

import com.lagou.dao.AccountDao;
import com.lagou.domain.Account;
import com.lagou.service.AccountService;
import com.lagou.utils.TransactionManage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("accountService")
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private TransactionManage transactionManage;

    @Override
    public List<Account> findAll() {
        return null;
    }

    @Override
    public Account findById(int id) {
        return null;
    }

    @Override
    public void save(Account account) {
        System.out.println("save方法");
    }

    @Override
    public void update(Account account) {
        System.out.println("update 方法");
    }

    @Override
    public void delete(int id) {
        System.out.println("delete方法");
    }

    @Override
    public void out(String outUser, double money) {
        accountDao.out(outUser, money);
    }

    @Override
    public void in(String inUser, double money) {
        accountDao.in(inUser, money);
    }

    @Override
    public void transfer(String outUser, String inUser, double money) {
        accountDao.out(outUser, money);
        accountDao.in(inUser, money);
    }
}
