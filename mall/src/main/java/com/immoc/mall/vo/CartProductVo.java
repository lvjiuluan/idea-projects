package com.immoc.mall.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class CartProductVo {
    private Integer productId;
    /*
     *
     * 购物车里面购买的数量
     * */

    private Integer quantity;

    private String productName;

    private String productSubtitle;

    private String productMainImage;

    private BigDecimal productPrice;

    private Integer productStatus;

    /*
     * productTotalPrice = quantity * productPrice
     * */

    private BigDecimal productTotalPrice;

    private Integer productStock;

    /*
     * 商品是否选中
     * */
    private Boolean productSelected;
}
