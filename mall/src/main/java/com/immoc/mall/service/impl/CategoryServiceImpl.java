package com.immoc.mall.service.impl;

import com.immoc.mall.dao.CategoryMapper;
import com.immoc.mall.pojo.Category;
import com.immoc.mall.service.ICategoryService;
import com.immoc.mall.vo.CategoryIdVo;
import com.immoc.mall.vo.CategoryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class CategoryServiceImpl implements ICategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    private void reccurentSort(List<CategoryVo> categoryVos) {
        if (categoryVos == null) return;
        categoryVos.sort(Comparator.comparingInt(CategoryVo::getSortOrder).reversed());
        for (CategoryVo categoryVo : categoryVos) {
            reccurentSort(categoryVo.getSubCategories());
        }

    }

    @Override
    public List<CategoryVo> queryAllCategory(Integer pid) {
        List<CategoryVo> categoryVos = categoryMapper.queryAllCategory(pid);
        reccurentSort(categoryVos);
        return categoryVos;
    }

    private void flatenList(List<CategoryIdVo> categoryIdVos, List<Integer> ids) {
        if (categoryIdVos == null) return;
        for (CategoryIdVo categoryIdVo : categoryIdVos) {
            ids.add(categoryIdVo.getId());
        }
        for (CategoryIdVo categoryIdVo : categoryIdVos) {
            flatenList(categoryIdVo.getSubIds(), ids);
        }
    }

    @Override
    public List<Integer> queryAllCategoryId(Integer pid) {
        List<CategoryIdVo> categoryIdVos = categoryMapper.queryAllCategoryId(pid);
        List<Integer> ids = new ArrayList<>();
        flatenList(categoryIdVos, ids);
        return ids;
    }
}
