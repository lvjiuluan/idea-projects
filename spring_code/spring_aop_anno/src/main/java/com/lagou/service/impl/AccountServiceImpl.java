package com.lagou.service.impl;

import com.lagou.service.AccountService;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {
    @Override
    public void transfer() {
        System.out.println("转账方法执行了");
    }
}
