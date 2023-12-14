package com.lagou.dao;

import com.lagou.entity.OrderItem;
import com.lagou.entity.Orders;
import com.lagou.entity.Product;
import com.lagou.utils.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OdersDao {
    // 1 获取uid为001的用户的所有订单信息
    public List<Orders> findAllOrdersByUid(String uid) throws SQLException {
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        String sql = "select * from orders where uid=?";
        List<Orders> orders = qr.query(sql, new BeanListHandler<Orders>(Orders.class), uid);
        return orders;
    }
    // 2 查询订单为order001的所有商品信息
    public  List<Product> findAllProductByOid(String oid) throws SQLException {
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        String sql = "select * from orderitem where oid=?";
        // 查询结果是多条订单项信息
        List<OrderItem> list = qr.query(sql, new BeanListHandler<OrderItem>(OrderItem.class), oid);
        // 创建集合，保存商品信息
        List<Product> productList = new ArrayList<>();
        // 遍历订单项目集合，获取pid
        ProductDao productDao = new ProductDao();
        for (OrderItem orderitem : list) {
            String pid = orderitem.getPid();
            Product product = productDao.findProductById(pid);
            productList.add(product);
            System.out.println(orderitem.getOid());
        }
        return productList;
    }
}
