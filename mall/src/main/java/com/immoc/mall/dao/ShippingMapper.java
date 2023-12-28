package com.immoc.mall.dao;

import com.immoc.mall.pojo.Shipping;

import java.util.List;

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
}