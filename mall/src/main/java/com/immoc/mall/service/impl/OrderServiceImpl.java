package com.immoc.mall.service.impl;

import com.immoc.mall.dao.OrderItemMapper;
import com.immoc.mall.dao.OrderMapper;
import com.immoc.mall.dao.ProductMapper;
import com.immoc.mall.dao.ShippingMapper;
import com.immoc.mall.enums.OrderStatusEnum;
import com.immoc.mall.enums.PaymentTypeEnum;
import com.immoc.mall.enums.ProductStatusEnum;
import com.immoc.mall.enums.ResponseEnum;
import com.immoc.mall.pojo.*;
import com.immoc.mall.service.IOrderService;
import com.immoc.mall.vo.OrderItemVo;
import com.immoc.mall.vo.OrderVo;
import com.immoc.mall.vo.ResponseVo;
import com.immoc.mall.vo.ShippingVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.xml.ws.Response;

import java.math.BigDecimal;
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
    @Autowired
    private OrderItemMapper orderItemMapper;

    @Override
    @Transactional
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
        Long orderNo = generateOrderNo();
        List<OrderItem> orderItemList = new ArrayList<>();
        for (Cart cart : cartList) {
            Product product = map.get(cart.getProductId());
            if (product == null) {
                // 购物车中的商品不在数据库中，会报错
                return ResponseVo.error(PRODUCT_NOT_EXIST,
                        "商品 " + cart.getProductId() + " 不存在");
            }
            // 商品处于下架状态
            if (!product.getStatus().equals(ProductStatusEnum.ON_SALE.getCode())) {
                return ResponseVo.error(PRODUCT_OFF_SALE_OR_DELETE);
            }
            // 判断数据中的商品库存是否充足
            if (product.getStock() < cart.getQuantity()) {
                return ResponseVo.error(PRODUCT_STOCK_NOT_ENOUGH,
                        "商品 " + product.getName() + " 库存不足");
            }
            // 构造order_item对象
            OrderItem orderItem = buildOrderItem(uid, orderNo, cart.getQuantity(), product);
            orderItemList.add(orderItem);
            // 减去库存
            product.setStock(product.getStock() - cart.getQuantity());
            // 保存product对象
            int rowForProduct = productMapper.updateByPrimaryKeySelective(product);
            if (rowForProduct <= 0) {
                return ResponseVo.error(ERROR);
            }
        }
        Order order = buildOrder(uid, orderNo, shippingId, orderItemList);
        // 3 计算价格
        // 3.1 只计算被选中的商品，才会下单写入order_item表中
        // 4 生成订单 入库
        // 4.1 order表
        // 4.2 order_item表
        // 这两个表必须要么同时成功写入，或者同时失败
        // 不能一个表成功，一个表失败，使用事务  先后没用关系

        int rowForOrder = orderMapper.insertSelective(order);
        if (rowForOrder <= 0) {
            return ResponseVo.error(ERROR);
        }
        int rowForOrderItem = orderItemMapper.batchInsert(orderItemList);

        if (rowForOrderItem <= 0) {
            return ResponseVo.error(ERROR);
        }
        // 5 减去库存
        // 6 更新的购物车，只删除选中的商品
        // 在最后再来操作redis，因为redis的事务是不能回滚的
        for (Cart cart : cartList) {
            cartService.delete(uid, cart.getProductId());
        }
        // 7 构造OrderVo对象并返回
        OrderVo orderVo = buildOrderVo(order, orderItemList, shipping);
        return ResponseVo.sucess(orderVo);
    }

    private OrderVo buildOrderVo(Order order, List<OrderItem> orderItemList, Shipping shipping) {
        OrderVo orderVo = new OrderVo();
        BeanUtils.copyProperties(order, orderVo);
        List<OrderItemVo> orderItemVoList = new ArrayList<>();
        for (OrderItem orderItem : orderItemList) {
            OrderItemVo orderItemVo = new OrderItemVo();
            BeanUtils.copyProperties(orderItem, orderItemVo);
            orderItemVoList.add(orderItemVo);
        }
        orderVo.setOrderItemVoList(orderItemVoList);
        orderVo.setShippingId(shipping.getId());
        ShippingVo shippingVo = new ShippingVo();
        BeanUtils.copyProperties(shipping, shippingVo);
        orderVo.setShippingVo(shippingVo);
        return orderVo;
    }

    private Long generateOrderNo() {
        return System.currentTimeMillis() + new Random().nextInt(999);
    }

    private OrderItem buildOrderItem(Integer uid, Long orderNo, Integer quantity, Product product) {
        OrderItem orderItem = new OrderItem();
        orderItem.setUserId(uid);
        orderItem.setOrderNo(orderNo);
        orderItem.setProductId(product.getId());
        orderItem.setProductName(product.getName());
        orderItem.setProductImage(product.getMainImage());
        orderItem.setCurrentUnitPrice(product.getPrice());
        orderItem.setQuantity(quantity);
        orderItem.setTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
        return orderItem;
    }

    private Order buildOrder(Integer uid,
                             Long orderNo,
                             Integer shippingId,
                             List<OrderItem> orderItemList) {
        Order order = new Order();
        order.setOrderNo(orderNo);
        order.setUserId(uid);
        order.setShippingId(shippingId);
        BigDecimal payment = BigDecimal.ZERO;
        for (OrderItem orderItem : orderItemList) {
            payment = payment.add(orderItem.getTotalPrice());
        }
        order.setPayment(payment);
        order.setPaymentType(PaymentTypeEnum.PAY_ONLINE.getCode());
        order.setPostage(0);
        order.setStatus(OrderStatusEnum.NOT_PAY.getCode());
        return order;
    }
}
