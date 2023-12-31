package com.immoc.mall.service.impl;

import com.github.pagehelper.PageInfo;
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
public class OrderServiceImplTest extends MallApplicationTests {
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

    @Test
    void list() {
        ResponseVo<PageInfo> responseVo = orderService.list(1, 1, 1);
        log.info("responseVo={}", gson.toJson(responseVo));
    }

    @Test
    void detail() {
        ResponseVo<OrderVo> responseVo = orderService.detail(2, 1703839983263l);
        log.info("responseVo={}", gson.toJson(responseVo));
    }

    @Test
    void cancel(){
        ResponseVo responseVo = orderService.cancel(1, 1703839983263l);
        log.info("responseVo={}", gson.toJson(responseVo));
    }
}