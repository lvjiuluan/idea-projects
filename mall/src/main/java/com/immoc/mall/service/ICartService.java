package com.immoc.mall.service;

import com.immoc.mall.form.CartAddForm;
import com.immoc.mall.vo.CartVo;
import com.immoc.mall.vo.ResponseVo;
import org.springframework.stereotype.Service;

public interface ICartService {
    /*
     * 购物车添加商品
     * */
    ResponseVo<CartVo> add(Integer uid,CartAddForm cartAddForm);
}
