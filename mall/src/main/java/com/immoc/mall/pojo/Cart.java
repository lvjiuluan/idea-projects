package com.immoc.mall.pojo;

import lombok.Data;

@Data
public class Cart {
    private Integer productId;
    /*
     *
     * 购物车里面购买的数量
     * */

    private Integer quantity;
    /*
     * 商品是否选中
     * */
    private Boolean productSelected;

    public Cart(Integer productId, Integer quantity, Boolean productSelected) {
        this.productId = productId;
        this.quantity = quantity;
        this.productSelected = productSelected;
    }

    public Cart() {
    }
}
