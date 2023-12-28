package com.immoc.mall.service.impl;

import com.immoc.mall.dao.ProductMapper;
import com.immoc.mall.enums.ResponseEnum;
import com.immoc.mall.form.CartAddForm;
import com.immoc.mall.pojo.Product;
import com.immoc.mall.service.ICartService;
import com.immoc.mall.vo.CartVo;
import com.immoc.mall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import static com.immoc.mall.enums.ProductStatusEnum.ON_SALE;
import static com.immoc.mall.enums.ResponseEnum.*;

@Service
public class CartServiceImpl implements ICartService {
    private final static String CAR_REDIS_KEY_FORMAT = "car_%d";
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public ResponseVo<CartVo> add(Integer uid, CartAddForm cartAddForm) {
        // 把商品加入购物车
        // 1 判断商品是否存在
        Product product = productMapper.selectByPrimaryKey(cartAddForm.getProductId());
        if (product == null) {
            return ResponseVo.error(PRODUCT_NOT_EXIST);
        }
        // 2 商品库存是否充足
        if (product.getStock() <= 0) {
            return ResponseVo.error(PRODUCT_STOCK_NOT_ENOUGH);
        }
        // 3 商品是否是正常在售
        if (!product.getStatus().equals(ON_SALE.getCode())) {
            return ResponseVo.error(PRODUCT_OFF_SALE_OR_DELETE);
        }
        // 4 将商品加入购物车
        // 写入到redis中
        // 4.1 引入依赖
        // 4.2 做配置
        // 4.3 写入内容
        // key: cart_1
        stringRedisTemplate.opsForValue().set(String.format(CAR_REDIS_KEY_FORMAT, uid),"");
        return null;
    }
}
