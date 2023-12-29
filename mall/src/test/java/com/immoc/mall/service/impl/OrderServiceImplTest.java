package com.immoc.mall.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.immoc.mall.MallApplicationTests;
import com.immoc.mall.vo.OrderVo;
import com.immoc.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class OrderServiceImplTest extends MallApplicationTests {
    @Autowired
    private OrderServiceImpl orderService;
    private Integer uid = 1;
    private Integer shippingId = 4;
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Test
    void create() {
        ResponseVo<OrderVo> responseVo = orderService.create(uid, shippingId);
        log.info("responseVo={}", gson.toJson(responseVo));
    }
}