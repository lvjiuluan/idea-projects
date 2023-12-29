package com.immoc.mall.service;

import com.immoc.mall.vo.OrderVo;
import com.immoc.mall.vo.ResponseVo;

import javax.xml.ws.Response;

public interface IOrderService {
    // 1.创建订单
    ResponseVo<OrderVo> create(Integer uid, Integer shippingId);
}
