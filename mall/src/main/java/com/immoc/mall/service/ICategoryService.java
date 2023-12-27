package com.immoc.mall.service;

import com.immoc.mall.pojo.Category;
import com.immoc.mall.vo.CategoryVo;

import java.util.List;

public interface ICategoryService {
    // 查询所有商品类目
    List<CategoryVo> queryAllCategory(Integer pid);

    // 查询所有父id为pid的直接子类别的Id
    List<Integer> queryAllCategoryId(Integer pid);
}
