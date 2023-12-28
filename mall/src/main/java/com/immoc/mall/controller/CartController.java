package com.immoc.mall.controller;

import com.immoc.mall.form.CartAddForm;
import com.immoc.mall.form.CartUpdateForm;
import com.immoc.mall.pojo.User;
import com.immoc.mall.service.impl.CartServiceImpl;
import com.immoc.mall.vo.CartVo;
import com.immoc.mall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static com.immoc.mall.consts.MallConst.CURRENT_USER;

@RestController
public class CartController {
    @Autowired
    private CartServiceImpl cartService;

    // 1.购物车List列表
    @GetMapping("/carts")
    public ResponseVo<CartVo> carts(HttpSession session) {
        User user = (User) session.getAttribute(CURRENT_USER);
        Integer uid = user.getId();
        return cartService.carts(uid);
    }

    // 2.购物车添加商品
    @PostMapping("/carts")
    public ResponseVo<CartVo> add(@Valid @RequestBody CartAddForm cartAddForm, HttpSession session) {
        User user = (User) session.getAttribute(CURRENT_USER);
        Integer uid = user.getId();
        return cartService.add(uid, cartAddForm);
    }

    // 3.更新购物车
    @PutMapping("/carts/{productId}")
    public ResponseVo<CartVo> update(@PathVariable Integer productId,
                                     @Valid @RequestBody CartUpdateForm cartUpdateForm,
                                     HttpSession session) {
        User user = (User) session.getAttribute(CURRENT_USER);
        Integer uid = user.getId();
        return cartService.update(uid, productId, cartUpdateForm);
    }

    // 4.移除购物车某个产品
    @DeleteMapping("/carts/{productId}")
    public ResponseVo<CartVo> delete(@PathVariable Integer productId,
                                     HttpSession session) {
        User user = (User) session.getAttribute(CURRENT_USER);
        Integer uid = user.getId();
        return cartService.delete(uid, productId);
    }

    // 5.全选中
    @PutMapping("/carts/selectAll")
    public ResponseVo<CartVo> selectAll(HttpSession session) {
        User user = (User) session.getAttribute(CURRENT_USER);
        Integer uid = user.getId();
        return cartService.selectAll(uid);
    }

    // 6.全不选
    @PutMapping("/carts/selectAll")
    public ResponseVo<CartVo> unSelectAll(HttpSession session) {
        User user = (User) session.getAttribute(CURRENT_USER);
        Integer uid = user.getId();
        return cartService.unSelectAll(uid);
    }

    //    7.获取购物中所有商品数量总和
    @GetMapping("/carts/products/sum")
    public ResponseVo<Integer> sum(HttpSession session) {
        User user = (User) session.getAttribute(CURRENT_USER);
        Integer uid = user.getId();
        return cartService.sum(uid);
    }
}
