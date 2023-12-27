package com.immoc.mall.dao;

import com.immoc.mall.pojo.Category;
import com.immoc.mall.vo.CategoryIdVo;
import com.immoc.mall.vo.CategoryVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CategoryMapper {
    @Select("select * from mall_category where id = #{id}")
    Category findById(Integer id);

    Category queryById(Integer id);

    List<CategoryVo> queryAllCategory(Integer pid);

    List<CategoryVo> queryChildrenCategory(Integer pid);

    // 查询所有父id为pid的直接子类别的Id
    List<CategoryIdVo> queryAllCategoryId(Integer pid);
}
