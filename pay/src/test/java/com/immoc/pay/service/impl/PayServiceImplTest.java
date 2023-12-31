package com.immoc.pay.service.impl;

import com.immoc.pay.PayApplicationTests;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.Instant;

class PayServiceImplTest extends PayApplicationTests {
    @Autowired
    PayServiceImpl payServiceImpl;
    @Autowired
    private AmqpTemplate amqpTemplate;

    @Test
    void create() {
        Instant now = Instant.now();
        long milliSeconds = now.toEpochMilli();
        payServiceImpl.create(String.valueOf(milliSeconds), BigDecimal.valueOf(0.01));
    }

    @Test
    void sendMQMsg() throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            amqpTemplate.convertAndSend("payNotify", "hello" + i);
            Thread.sleep(2000);
        }
    }
}