package com.immoc.mall.enums;

import lombok.Getter;

@Getter
public enum ResponseEnum {
    ERROR(-1, "服务端错误"),

    SUCCESS(0, "成功"),

    PASSWORD_ERROR(1, "密码错误"),

    USERNAME_EXIST(2, "用户名已存在"),

    EMAIL_EXIST(4, "邮箱已存在"),

    NEED_LOGIN(10, "用户未登录，请先登录"),

    PARAM_ERROR(3, "参数错误"),

    UERNAME_OR_PASSWORD_ERROR(11, "用户名或密码错误"),

    PRODUCT_OFF_SALE_OR_DELETE(12, "商品下架或删除"),

    PRODUCT_NOT_EXIST(13, "商品不存在"),

    PRODUCT_STOCK_NOT_ENOUGH(13, "库存不足"),

    CART_PRODUCT_NOT_EXIST(15, "购物车里无商品不存在"),

    ADD_SHIPPING_FAILURE(16, "新建地址失败"),

    SHIPPING_NOT_EXIST(17, "地址不存在"),

    DELETE_SHIPPING_FAILURE(18, "删除地址失败"),

    NO_RIGHT_DELETE_OR_UPDATE_SHIPPING(18, "没有权限删除或修改该地址"),

    UPDATE_SHIPPING_FAILURE(18, "更新地址失败"),

    CART_SELECTED_IS_EMPTY(19, "请选择商品后下单"),

    NO_RIGHT_SELECT_ORDER(18, "没有权限查询该订单"),

    ORDER_NOT_EXIST(19, "没有找到订单"),

    ORDER_STATUS_ERROR(20, "订单状态有误"),

    ;

    Integer code;
    String desc;

    ResponseEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
