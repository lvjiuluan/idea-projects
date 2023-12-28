package com.immoc.mall.service.impl;

import com.google.gson.Gson;
import com.immoc.mall.dao.ProductMapper;
import com.immoc.mall.enums.ResponseEnum;
import com.immoc.mall.form.CartAddForm;
import com.immoc.mall.form.CartUpdateForm;
import com.immoc.mall.pojo.Cart;
import com.immoc.mall.pojo.Product;
import com.immoc.mall.service.ICartService;
import com.immoc.mall.vo.CartProductVo;
import com.immoc.mall.vo.CartVo;
import com.immoc.mall.vo.ResponseVo;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.immoc.mall.enums.ProductStatusEnum.ON_SALE;
import static com.immoc.mall.enums.ResponseEnum.*;

@Service
public class CartServiceImpl implements ICartService {
    private final static String CAR_REDIS_KEY_FORMAT = "cart_%d";
    private Gson gson = new Gson();
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Override
    public ResponseVo<CartVo> add(Integer uid, CartAddForm cartAddForm) {

        Integer quantity = 1;

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
        // value: 购物车数据 只需存productId、quantity、是否选中，其他从数据库里面读取
        // value: Cart 对象
//        stringRedisTemplate.opsForValue().set(String.format(CAR_REDIS_KEY_FORMAT, uid),
//                gson.toJson(new Cart(cartAddForm.getProductId(), quantity, cartAddForm.getSelected())));
        // 用map结构
        HashOperations<String, String, String> opsForHash = stringRedisTemplate.opsForHash();
        String redisKey = String.format(CAR_REDIS_KEY_FORMAT, uid);
        // 在把数量添加到redis时，要先把数量读出来
        String value = opsForHash.get(redisKey, String.valueOf(cartAddForm.getProductId()));
        Cart cart;
        if (StringUtils.isNullOrEmpty(value)) {
            // redis没有该商品、
            // 新增对象
            cart = new Cart(cartAddForm.getProductId(), quantity, cartAddForm.getSelected());
        } else {
            // redis已经有了该商品
            // 只增加数量
            // 反序列化生成对象
            cart = gson.fromJson(value, Cart.class);
            cart.setQuantity(cart.getQuantity() + quantity);
        }
        opsForHash.put(redisKey,
                String.valueOf(cartAddForm.getProductId()),
                gson.toJson(cart));
        return carts(uid);
    }

    @Override
    public ResponseVo<CartVo> carts(Integer uid) {
        CartVo cartVo = new CartVo();
        List<CartProductVo> cartProductVoList = new ArrayList<>();
        Boolean selectedAll = true;
        BigDecimal cartTotalPrice = BigDecimal.valueOf(0);
        Integer cartTotalQuantity = 0;
        // 用map结构获取
        HashOperations<String, String, String> opsForHash = stringRedisTemplate.opsForHash();
        String redisKey = String.format(CAR_REDIS_KEY_FORMAT, uid);
        Map<String, String> entries = opsForHash.entries(redisKey);
        for (Map.Entry<String, String> entry : entries.entrySet()) {
            Integer productId = Integer.valueOf(entry.getKey());// productID
            Cart cart = gson.fromJson(entry.getValue(), Cart.class);// Cart对象
            // TODO 不要在循环中查询数据，需要优化，使用mysql里面的in
            // 从数据库里面查询
            Product product = productMapper.selectByPrimaryKey(productId);
            // 如果查出来的不是null
            if (product != null) {
                CartProductVo cartProductVo = new CartProductVo(
                        cart.getProductId(),
                        cart.getQuantity(),
                        product.getName(),
                        product.getSubtitle(),
                        product.getMainImage(),
                        product.getPrice(),
                        product.getStatus(),
                        product.getPrice().multiply(BigDecimal.valueOf(cart.getQuantity())),
                        product.getStock(),
                        cart.getProductSelected()
                );
                cartProductVoList.add(cartProductVo);
                selectedAll = selectedAll & cart.getProductSelected();
                if (cart.getProductSelected()) {
                    // 注意Bigdecimal add方法不会改变调用的对象的值
                    // 只计算选中的
                    cartTotalPrice = cartTotalPrice.add(cartProductVo.getProductTotalPrice());
                    cartTotalQuantity += cart.getQuantity();
                }
            }
        }
        cartVo.setCartProductVoList(cartProductVoList);
        // 设置其他三个字段
        /*
        * private Boolean selectedAll;
          private BigDecimal cartTotalPrice;
          private Integer cartTotalQuantity;
        * */
        cartVo.setSelectedAll(selectedAll);
        cartVo.setCartTotalPrice(cartTotalPrice);
        cartVo.setCartTotalQuantity(cartTotalQuantity);
        return ResponseVo.sucess(cartVo);
    }


    /*
     * 更新购物车某个商品的内容
     * */
    @Override
    public ResponseVo<CartVo> update(Integer uid, Integer productId, CartUpdateForm cartUpdateForm) {
        // 要更新更新购物车某个商品的内容，首先要把该商品查出来
        // 更加 uid 和 productId将商品查出来
        HashOperations<String, String, String> opsForHash = stringRedisTemplate.opsForHash();
        String redisKey = String.format(CAR_REDIS_KEY_FORMAT, uid);
        // 在把数量添加到redis时，要先把数量读出来
        String value = opsForHash.get(redisKey, String.valueOf(productId));
        Cart cart = gson.fromJson(value, Cart.class);
        if (StringUtils.isNullOrEmpty(value)) {
            // redis没有该商品 报错
            return ResponseVo.error(CART_PRODUCT_NOT_EXIST);
        }
        // redis已经有了该商品
        // 对购物车里的商品进行修改
        // 判断传的什么值
        if (cartUpdateForm.getQuantity() != null && cartUpdateForm.getQuantity() >= 0) {
            cart.setQuantity(cartUpdateForm.getQuantity());
        }
        if (cartUpdateForm.getSelected() != null) {
            cart.setProductSelected(cartUpdateForm.getSelected());
        }
        // 将修改后的cart更新
        opsForHash.put(redisKey, String.valueOf(productId), gson.toJson(cart));
        return carts(uid);
    }

    @Override
    public ResponseVo<CartVo> delete(Integer uid, Integer productId) {
        // 先读取有没有该商品
        HashOperations<String, String, String> opsForHash = stringRedisTemplate.opsForHash();
        String redisKey = String.format(CAR_REDIS_KEY_FORMAT, uid);
        String value = opsForHash.get(redisKey, String.valueOf(productId));
        Cart cart = gson.fromJson(value, Cart.class);
        if (StringUtils.isNullOrEmpty(value)) {
            // redis没有该商品 报错
            return ResponseVo.error(CART_PRODUCT_NOT_EXIST);
        }
        // redis已经有了该商品 删除
        opsForHash.delete(redisKey, String.valueOf(productId));
        return carts(uid);
    }

    private List<Cart> listForCart(Integer uid) {
        List<Cart> cartList = new ArrayList<>();
        // 用map结构获取
        HashOperations<String, String, String> opsForHash = stringRedisTemplate.opsForHash();
        String redisKey = String.format(CAR_REDIS_KEY_FORMAT, uid);
        Map<String, String> entries = opsForHash.entries(redisKey);
        for (Map.Entry<String, String> entry : entries.entrySet()) {
            Cart cart = gson.fromJson(entry.getValue(), Cart.class);// Cart对象
            cartList.add(cart);
        }
        return cartList;
    }

    @Override
    public ResponseVo<CartVo> selectAll(Integer uid) {
        HashOperations<String, String, String> opsForHash = stringRedisTemplate.opsForHash();
        String redisKey = String.format(CAR_REDIS_KEY_FORMAT, uid);
        // 购物出中的商品遍历一遍
        List<Cart> cartList = listForCart(uid);
        for (Cart cart : cartList) {
            cart.setProductSelected(true);
            opsForHash.put(redisKey, String.valueOf(cart.getProductId()), gson.toJson(cart));
        }
        return carts(uid);
    }

    @Override
    public ResponseVo<CartVo> unSelectAll(Integer uid) {
        HashOperations<String, String, String> opsForHash = stringRedisTemplate.opsForHash();
        String redisKey = String.format(CAR_REDIS_KEY_FORMAT, uid);
        // 购物出中的商品遍历一遍
        List<Cart> cartList = listForCart(uid);
        for (Cart cart : cartList) {
            cart.setProductSelected(false);
            opsForHash.put(redisKey, String.valueOf(cart.getProductId()), gson.toJson(cart));
        }
        return carts(uid);
    }

    @Override
    public ResponseVo<Integer> sum(Integer uid) {
        Integer sum = 0;
        HashOperations<String, String, String> opsForHash = stringRedisTemplate.opsForHash();
        String redisKey = String.format(CAR_REDIS_KEY_FORMAT, uid);
        // 购物出中的商品遍历一遍
        List<Cart> cartList = listForCart(uid);
        for (Cart cart : cartList) {
            sum += cart.getQuantity();
        }
        return ResponseVo.sucess(sum);
    }
}
