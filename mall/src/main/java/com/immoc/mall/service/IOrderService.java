package com.immoc.mall.service;

import com.github.pagehelper.PageInfo;
import com.immoc.mall.vo.OrderVo;
import com.immoc.mall.vo.ResponseVo;

import javax.xml.ws.Response;

public interface IOrderService {
    // 1.创建订单
    ResponseVo<OrderVo> create(Integer uid, Integer shippingId);

    // 2.订单列表详情,分页查询
    ResponseVo<PageInfo> list(Integer uid, Integer pageNum, Integer pageSize);

    // 3.根据订单编号返回订单详情, 只能查询自己的订单
    ResponseVo<OrderVo> detail(Integer uid, Long orderNo);

    // 4.取消订单
    ResponseVo cancel(Integer uid, Long orderNo);

    // 5. 修改订单状态为已支付
    void paid(Long orderNo);
}
