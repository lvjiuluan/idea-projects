package com.immoc.mall.enums;

import lombok.Getter;

@Getter
public enum OrderStatusEnum {
    /*
     * 订单状态:0-已取消-10-未付款，20-已付款，40-已发货，50-交易成功，60-交易关闭
     * */
    CANCELED(0, "已取消"),
    NOT_PAY(10, "未付款"),
    PAYED(20, "已付款"),
    SHIPPED(40, "已发货"),
    TRIP_SUCCESS(50, "交易成功"),
    TRIP_CLOSED(60, "交易关闭"),
    ;
    Integer code;
    String desc;

    OrderStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
