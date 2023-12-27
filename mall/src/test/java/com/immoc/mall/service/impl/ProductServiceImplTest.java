package com.immoc.mall.service.impl;

import com.github.pagehelper.PageInfo;
import com.immoc.mall.MallApplicationTests;
import com.immoc.mall.enums.ResponseEnum;
import com.immoc.mall.vo.ProductDetailVo;
import com.immoc.mall.vo.ProductVo;
import com.immoc.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceImplTest extends MallApplicationTests {

    @Autowired
    private ProductServiceImpl productService;

    @Test
    void list() {
        ResponseVo<PageInfo> responseVo = productService.list(null, 1, 2);
        System.out.println(responseVo);
    }

    @Test
    void detail() {
        ResponseVo<ProductDetailVo> responseVo = productService.detail(26);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
    }
}