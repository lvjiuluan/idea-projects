package com.immoc.mall.vo;

import com.immoc.mall.pojo.Category;
import lombok.Data;

import java.util.List;

@Data
public class CategoryVo {
    private Integer id;
    private Integer parentId;
    private String name;
    private Integer status;
    private Integer sortOrder;
    private List<CategoryVo> subCategories;
}
