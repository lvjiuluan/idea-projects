package com.immoc.mall.dao;

import com.immoc.mall.pojo.Product;
import com.immoc.mall.vo.ProductVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product row);

    int insertSelective(Product row);

    // 根据product_id 查询
    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product row);

    int updateByPrimaryKey(Product row);

    List<ProductVo> selectByCategoryIdList(@Param("categoryIdList") List<Integer> categoryIdList);
}