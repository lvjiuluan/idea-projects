package com.immoc.mall.controller;

import com.immoc.mall.pojo.Category;
import com.immoc.mall.service.impl.CategoryServiceImpl;
import com.immoc.mall.vo.CategoryVo;
import com.immoc.mall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryController {
    @Autowired
    private CategoryServiceImpl categoryService;

    @GetMapping("/categories")
    public ResponseVo<List<CategoryVo>> queryAllCategory() {
        List<CategoryVo> categories = categoryService.queryAllCategory(0);
        return ResponseVo.sucess(categories);
    }
}
