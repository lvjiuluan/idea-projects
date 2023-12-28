package com.immoc.mall.service;

import com.immoc.mall.form.CartAddForm;
import com.immoc.mall.form.CartUpdateForm;
import com.immoc.mall.vo.CartVo;
import com.immoc.mall.vo.ResponseVo;
import org.springframework.stereotype.Service;

public interface ICartService {
    /*
     * 购物车添加商品
     * */
    ResponseVo<CartVo> add(Integer uid, CartAddForm cartAddForm);

    /*
     * 获取购物车列表
     * */
    ResponseVo<CartVo> carts(Integer uid);

    /*
     *
     * 更新购物车
     * */
    ResponseVo<CartVo> update(Integer uid, Integer productId, CartUpdateForm cartUpdateForm);

    /*
     *
     * 删除购物车
     * */
    ResponseVo<CartVo> delete(Integer uid, Integer productId);

    /*
     *
     * 购物车全选
     * */
    ResponseVo<CartVo> selectAll(Integer uid);

    /*
     *
     * 购物车全不选
     * */
    ResponseVo<CartVo> unSelectAll(Integer uid);

    /*
     *
     * 获取购物车数量总和
     * */
    ResponseVo<Integer> sum(Integer uid);
}
