package com.immoc.mall.dao;

import com.immoc.mall.MallApplicationTests;
import com.immoc.mall.pojo.Product;
import com.immoc.mall.vo.ProductVo;
import com.immoc.mall.vo.ResponseVo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProductMapperTest extends MallApplicationTests {
    @Autowired
    private ProductMapper productMapper;

    @Test
    public void selectByCategoryIdList() {
        List<Integer> list = new ArrayList<>();
        list.add(100006);
        list.add(100008);
        List<ProductVo> productVoList = productMapper.selectByCategoryIdList(list);

    }

}