package com.example.spring.service.impl;

import com.example.spring.service.EmailService;

public class EmailServiceImpl implements EmailService {
    public EmailServiceImpl() {
        System.out.println("EmailServiceImpl构造方法被调用了" + this);
    }
}
