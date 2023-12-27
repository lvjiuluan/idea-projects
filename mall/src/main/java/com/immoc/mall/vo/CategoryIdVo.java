package com.immoc.mall.vo;

import lombok.Data;

import java.util.List;

@Data
public class CategoryIdVo {
    private Integer id;
    private List<CategoryIdVo> subIds;
}
