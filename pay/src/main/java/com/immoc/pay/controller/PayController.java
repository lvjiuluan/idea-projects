package com.immoc.pay.controller;

import com.immoc.pay.service.impl.PayService;
import com.lly835.bestpay.model.PayResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/pay")
@Slf4j
public class PayController {
    @Autowired
    private PayService payService;

    @GetMapping("/create")
    public ModelAndView create(@RequestParam("orderId") String orderId,
                               @RequestParam("amount") BigDecimal amount) {
        Instant now = Instant.now();
        long milliSeconds = now.toEpochMilli();
        orderId = milliSeconds + orderId;
        PayResponse payResponse = payService.create(orderId + orderId, amount);
        Map map = new HashMap<>();
        map.put("codeUrl", payResponse.getCodeUrl());
        map.put("orderId", orderId);
        map.put("amount", amount);
        return new ModelAndView("create", map);
    }

    @RequestMapping("/hello")
    @ResponseBody
    public String hello() {
        return "hello";
    }


    // 接收异步通知
    @PostMapping("/notify")
    @ResponseBody
    public String asyncNotify(@RequestBody String notifyData) {
        return payService.asyncNotify(notifyData);
    }
}
