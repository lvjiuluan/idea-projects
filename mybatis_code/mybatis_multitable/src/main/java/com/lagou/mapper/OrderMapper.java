package com.lagou.mapper;

import com.lagou.domain.Orders;

import java.util.List;

public interface OrderMapper {
    /**
     * 一对一 关联查询，出现出每个订单所属的用户信息
     */
    public List<Orders> findAllWithUser();

    /**
     * 一对一 嵌套查询，出现出每个订单所属的用户信息
     */
    public List<Orders> findAllWithUser2();

    /**
     * 根据uid 查询对应订单
     */
    public List<Orders> findByUid(int uid);
}
