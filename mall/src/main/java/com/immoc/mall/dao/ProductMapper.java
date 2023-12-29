package com.immoc.mall.dao;

import com.immoc.mall.pojo.Product;
import com.immoc.mall.vo.ProductVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product row);

    int insertSelective(Product row);

    // 根据product_id 查询
    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product row);

    int updateByPrimaryKey(Product row);

    List<ProductVo> selectByCategoryIdList(@Param("categoryIdList") List<Integer> categoryIdList);

    List<Product> selectByProductIdSet(@Param("productIdSet") Set<Integer> productIdSet);
}