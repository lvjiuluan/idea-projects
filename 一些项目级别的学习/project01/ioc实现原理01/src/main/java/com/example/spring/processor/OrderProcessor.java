package com.example.spring.processor;

import com.example.spring.service.EmailService;
import com.example.spring.service.OrderService;

public class OrderProcessor {
    private OrderService orderService;
    private EmailService emailService;

    public OrderProcessor() {
        System.out.println("OrderProcessor构造方法被调用了" + this);
    }

    public void setOrderService(OrderService orderService) {
        System.out.println("依赖OrderService，注入了");
        this.orderService = orderService;
    }

    public void setEmailService(EmailService emailService) {
        System.out.println("依赖EmailService，注入了");
        this.emailService = emailService;
    }
}
