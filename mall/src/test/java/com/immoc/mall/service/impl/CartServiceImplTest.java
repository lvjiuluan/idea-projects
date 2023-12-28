package com.immoc.mall.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.immoc.mall.MallApplicationTests;
import com.immoc.mall.form.CartAddForm;
import com.immoc.mall.form.CartUpdateForm;
import com.immoc.mall.vo.CartVo;
import com.immoc.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class CartServiceImplTest extends MallApplicationTests {
    @Autowired
    private CartServiceImpl cartService;
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Test
    public void add() {
        CartAddForm cartAddForm = new CartAddForm();
        cartAddForm.setProductId(28);
        cartAddForm.setSelected(false);
        ResponseVo<CartVo> responseVo = cartService.add(1, cartAddForm);
        log.info("responseVo={}", gson.toJson(responseVo));
    }

    @Test
    public void carts() {
        ResponseVo<CartVo> responseVo = cartService.carts(1);
        log.info("responseVo={}", gson.toJson(responseVo));
    }

    @Test
    public void update() {
        CartUpdateForm cartUpdateForm = new CartUpdateForm();
        cartUpdateForm.setQuantity(10);
        cartUpdateForm.setSelected(false);
        ResponseVo<CartVo> responseVo = cartService.update(1, 29, cartUpdateForm);
        log.info("responseVo={}", gson.toJson(responseVo));
    }

    @Test
    public void delet() {
        ResponseVo<CartVo> responseVo = cartService.delete(1, 27);
        log.info("responseVo={}", gson.toJson(responseVo));
    }

    @Test
    public void selectAll() {
        ResponseVo<CartVo> responseVo = cartService.selectAll(1);
        log.info("responseVo={}", gson.toJson(responseVo));
    }

    @Test
    public void unSelectAll() {
        ResponseVo<CartVo> responseVo = cartService.unSelectAll(1);
        log.info("responseVo={}", gson.toJson(responseVo));
    }

    @Test
    public void sum() {
        ResponseVo<Integer> responseVo = cartService.sum(1);
        log.info("responseVo={}", gson.toJson(responseVo));
    }
}