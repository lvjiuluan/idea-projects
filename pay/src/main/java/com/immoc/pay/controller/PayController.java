package com.immoc.pay.controller;

import com.immoc.pay.pojo.PayInfo;
import com.immoc.pay.service.impl.PayServiceImpl;
import com.lly835.bestpay.config.WxPayConfig;
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
    private PayServiceImpl payServiceImpl;
    @Autowired
    private WxPayConfig wxPayConfig;

    @GetMapping("/create")
    public ModelAndView create(@RequestParam("orderId") String orderId,
                               @RequestParam("amount") BigDecimal amount) {
//        Instant now = Instant.now();
//        long milliSeconds = now.toEpochMilli();
//        milliSeconds = milliSeconds / 100;
//        orderId = milliSeconds + orderId;
        PayResponse payResponse = payServiceImpl.create(orderId, amount);
        Map map = new HashMap<>();
        map.put("codeUrl", payResponse.getCodeUrl());
        map.put("orderId", orderId);
        map.put("amount", amount);
        map.put("returnUrl", wxPayConfig.getReturnUrl());
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
        return payServiceImpl.asyncNotify(notifyData);
    }

    @GetMapping("/queryByOrderId")
    @ResponseBody
    public PayInfo queryByOrderId(@RequestParam String orderId) {
        return payServiceImpl.queryByOrderId(orderId);
    }
}
