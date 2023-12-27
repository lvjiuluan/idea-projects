package com.immoc.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.immoc.mall.dao.CategoryMapper;
import com.immoc.mall.dao.ProductMapper;
import com.immoc.mall.pojo.Product;
import com.immoc.mall.service.IProductService;
import com.immoc.mall.vo.ProductDetailVo;
import com.immoc.mall.vo.ProductVo;
import com.immoc.mall.vo.ResponseVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.immoc.mall.enums.ProductStatusEnum.DELETE;
import static com.immoc.mall.enums.ProductStatusEnum.OFF_SALE;
import static com.immoc.mall.enums.ResponseEnum.PRODUCT_OFF_SALE_OR_DELETE;

@Service
public class ProductServiceImpl implements IProductService {
    @Autowired
    private CategoryServiceImpl categoryService;
    @Autowired
    private ProductMapper productMapper;

    @Override
    public ResponseVo<PageInfo> list(Integer categoryId, Integer pageNum, Integer pageSize) {
        List<Integer> categoryIdList = null;
        // 把categoryId的子类查出来
        if (categoryId != null) {
            categoryIdList = categoryService.queryAllCategoryId(categoryId);
            categoryIdList.add(categoryId);
        }
        PageHelper.startPage(pageNum, pageSize);
        // 根据categoryIdList查询所有的商品
        List<ProductVo> productVoList = productMapper.selectByCategoryIdList(categoryIdList);
        PageInfo pageInfo = new PageInfo(productVoList);
        pageInfo.setList(productVoList);
        return ResponseVo.sucess(pageInfo);
    }

    @Override
    public ResponseVo<ProductDetailVo> detail(Integer productId) {
        Product product = productMapper.selectByPrimaryKey(productId);
        // 如果商品已下架或者已删除，要返回错误
        // 只对确定性条件判断
        if (product.getStatus().equals(OFF_SALE.getCode()) || product.getStatus().equals(DELETE.getCode())) {
            return ResponseVo.error(PRODUCT_OFF_SALE_OR_DELETE);
        }
        ProductDetailVo productDetailVo = new ProductDetailVo();
        BeanUtils.copyProperties(product, productDetailVo);
        // 敏感数据处理
        product.setStock(product.getStock() > 100 ? 100 : product.getStock());
        return ResponseVo.sucess(productDetailVo);
    }
}
