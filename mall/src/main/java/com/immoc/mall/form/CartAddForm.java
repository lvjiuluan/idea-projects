package com.immoc.mall.form;

import lombok.Data;

import javax.validation.constraints.NotNull;


/*
 * 添加商品时的表单
 * */
@Data
public class CartAddForm {
    @NotNull
    private Integer productId;

    private Boolean selected = true;
}
