package com.immoc.mall.dao;

import com.immoc.mall.MallApplicationTests;
import com.immoc.mall.pojo.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class OrderItemMapperTest extends MallApplicationTests {
    @Autowired
    OrderMapper orderMapper;

    @Test
    void deleteByPrimaryKey() {
        Order order = orderMapper.selectByPrimaryKey(1);
        System.out.println(order);
    }

    @Test
    void insert() {
    }

    @Test
    void insertSelective() {
    }

    @Test
    void selectByPrimaryKey() {
    }

    @Test
    void updateByPrimaryKeySelective() {
    }

    @Test
    void updateByPrimaryKey() {
    }
}