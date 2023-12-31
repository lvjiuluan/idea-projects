package com.immoc.mall.dao;

import com.immoc.mall.pojo.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Order row);

    int insertSelective(Order row);

    Order selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Order row);

    int updateByPrimaryKey(Order row);

    List<Order> selectByUserId(@Param("user_id") Integer uid);

    Order selectByOderNo(Long orderNo);
}