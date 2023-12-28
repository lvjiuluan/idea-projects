package com.immoc.mall.controller;

import com.immoc.mall.form.CartAddForm;
import com.immoc.mall.vo.CartVo;
import com.immoc.mall.vo.ResponseVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class CartController {
    @PostMapping("/carts")
    public ResponseVo<CartVo> add(@Valid @RequestBody CartAddForm cartAddForm) {

        return null;
    }
}
