package com.immoc.mall.service;

import com.github.pagehelper.PageInfo;
import com.immoc.mall.vo.ProductDetailVo;
import com.immoc.mall.vo.ProductVo;
import com.immoc.mall.vo.ResponseVo;

import java.util.List;

public interface IProductService {
    ResponseVo<PageInfo> list(Integer categoryId, Integer pageNumber, Integer pageSize);

    // 根据produc_id查询
    public ResponseVo<ProductDetailVo> detail(Integer productId);
}
