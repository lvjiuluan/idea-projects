package com.immoc.mall.dao;

import com.immoc.mall.MallApplicationTests;
import com.immoc.mall.pojo.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class CategoryMapperTest extends MallApplicationTests {
    @Autowired
    private CategoryMapper categoryMapper;

    @Test
    void contextLoads() {
        Category category = categoryMapper.findById(100001);
        System.out.println(category);
    }

    @Test
    void test01() {
        Category category = categoryMapper.queryById(100001);
        System.out.println(category);
    }

    @Test
    void findById() {
    }

    @Test
    void queryById() {
    }
}