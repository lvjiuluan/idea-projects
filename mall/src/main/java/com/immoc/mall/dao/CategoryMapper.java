package com.immoc.mall.dao;

import com.immoc.mall.pojo.Category;
import org.apache.ibatis.annotations.Select;

public interface CategoryMapper {
    @Select("select * from mall_category where id = #{id}")
    Category findById(Integer id);

    Category queryById(Integer id);
}
