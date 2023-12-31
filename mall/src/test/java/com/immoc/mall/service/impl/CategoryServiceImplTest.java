package com.immoc.mall.service.impl;

import com.immoc.mall.MallApplicationTests;
import com.immoc.mall.pojo.Category;
import com.immoc.mall.vo.CategoryVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CategoryServiceImplTest extends MallApplicationTests {

    @Autowired
    private CategoryServiceImpl categoryService;

    @Test
    void queryAllCategory() {
        List<CategoryVo> categories = categoryService.queryAllCategory(0);
        System.out.println(categories);
    }
    @Test
    void queryAllCategoryId(){
        List<Integer> list = categoryService.queryAllCategoryId(100001);
        System.out.println(list);
    }
}