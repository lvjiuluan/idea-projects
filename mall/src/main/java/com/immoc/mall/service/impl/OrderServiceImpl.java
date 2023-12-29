package com.immoc.mall.service.impl;

import com.immoc.mall.dao.OrderMapper;
import com.immoc.mall.dao.ProductMapper;
import com.immoc.mall.dao.ShippingMapper;
import com.immoc.mall.enums.ResponseEnum;
import com.immoc.mall.pojo.Cart;
import com.immoc.mall.pojo.Product;
import com.immoc.mall.pojo.Shipping;
import com.immoc.mall.service.IOrderService;
import com.immoc.mall.vo.OrderVo;
import com.immoc.mall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.xml.ws.Response;

import java.util.*;

import static com.immoc.mall.enums.ResponseEnum.*;

@Service
public class OrderServiceImpl implements IOrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private ShippingMapper shippingMapper;
    @Autowired
    private CartServiceImpl cartService;
    @Autowired
    private ProductMapper productMapper;

    @Override
    public ResponseVo<OrderVo> create(Integer uid, Integer shippingId) {
        // 1 校验收货地址(总之收货地址要查出来)
        Shipping shipping = shippingMapper.selectByUserIdAndShippingId(uid, shippingId);
        if (shipping == null) {
            // 没有该地址
            return ResponseVo.error(SHIPPING_NOT_EXIST);
        }
        // 2 下单的商品要通过uid获取购物车里面选择的商品
        // 从redis里面获取
        List<Cart> cartList = cartService.listForCartSelected(uid);
        // 2.1 是否有商品
        if (CollectionUtils.isEmpty(cartList)) {
            return ResponseVo.error(CART_SELECTED_IS_EMPTY);
        }
        // 2.2 数据库中是否有这些商品，以及库存是否足够
       /* for (Cart cart : cartList) {
            // 根据productId查询数据库
            // 不建议在for循环里执行sql
        }*/
        // 获取cartList里面的productList
        Set<Integer> productIdSet = new HashSet<>();
        for (Cart cart : cartList) {
            productIdSet.add(cart.getProductId());
        }
        // 在数据库中用in productIdSet查询所有的商品
        // 通过productMapper一次性查出来
        List<Product> productList = productMapper.selectByProductIdSet(productIdSet);
        Map<Integer, Product> map = new HashMap<>();
        for (Product product : productList) {
            map.put(product.getId(), product);
        }
        for (Cart cart : cartList) {
            Product product = map.get(cart.getProductId());
            if (product == null) {
                // 购物车中的商品不在数据库中，会报错
                return ResponseVo.error(PRODUCT_NOT_EXIST,
                        "商品 " + cart.getProductId() + " 不存在");
            }

            // 判断数据中的商品库存是否充足
            if (product.getStock() < cart.getQuantity()) {
                return ResponseVo.error(PRODUCT_STOCK_NOT_ENOUGH,
                        "商品 " + product.getName() + " 库存不足");
            }
        }

        // 3 计算价格
        // 3.1 只计算被选中的商品，才会下单写入order_item表中
        // 4 生成订单 入库
        // 4.1 order表
        // 4.2 order_item表
        // 这两个表必须要么同时成功写入，或者同时失败
        // 不能一个表成功，一个表失败，使用事务
        // 5 减去库存
        // 6 更新的购物车，只删除选中的商品
        // 7 构造OrderVo对象并返回
        return null;
    }
}
