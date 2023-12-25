package com.immoc.pay.service.impl;

import com.immoc.pay.PayApplicationTests;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class PayServiceTest extends PayApplicationTests {
    @Autowired
    PayService payService;

    @Test
    void create() {
        Instant now = Instant.now();
        long milliSeconds = now.toEpochMilli();
        payService.create(String.valueOf(milliSeconds), BigDecimal.valueOf(0.01));
    }
}