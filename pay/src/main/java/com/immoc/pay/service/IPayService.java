package com.immoc.pay.service;

import com.lly835.bestpay.model.PayResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


public interface IPayService {
    // 发起支付
    PayResponse create(String orderId, BigDecimal amount);
    // 异步通知处理
    String asyncNotify(String notifyData);
}
