package com.lagou.service.impl;

import com.lagou.service.AccountService;

public class AccountServiceImpl implements AccountService {
    /**
     * 切点，要进行增强的方法
     */
    @Override
    public void transfer() {
        System.out.println("转账方法实现了");
        int i = 1 / 0;
    }
}
