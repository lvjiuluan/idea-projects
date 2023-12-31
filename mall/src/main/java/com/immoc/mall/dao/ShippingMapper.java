package com.immoc.mall.dao;

import com.immoc.mall.pojo.Shipping;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface ShippingMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Shipping row);

    int insertSelective(Shipping row);

    Shipping selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Shipping row);

    int updateByPrimaryKey(Shipping row);

    // 插入并返回主键id值
    Integer insertSelectiveId(Shipping row);

    List<Shipping> selectByUserId(Integer uid);

    Shipping selectByUserIdAndShippingId(@Param("user_id") Integer uid, @Param("id") Integer shippingId);

    List<Shipping> selectByShippingIdSet(@Param("shippingIdSet")  Set<Integer> shippingIdSet);
}