package com.lagou.dao;

import com.lagou.entity.Category;
import com.lagou.entity.Product;
import com.lagou.utils.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;

public class ProductDao {
    // 根据商品的id获取商品名称，价格以及商品分类的信息
    public Product findProductById(String pid) throws SQLException {
        // 1 创建QueryRunner
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        // 2 编写sql
        String sql = "select * from product where pid=?";
        Product product = qr.query(sql, new BeanHandler<Product>(Product.class), pid);
        // 3 获取对应类别的id
        String cid = product.getCid();
        // 4 通过cid查询分类信息
        Category category = findCategoryById(cid);
        product.setCategory(category);
        return product;
    }
    public Category findCategoryById(String cid) throws SQLException {
        // 1 创建QueryRunner
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        // 2 编写sql
        String sql = "select * from category where cid=?";
        Category category = qr.query(sql, new BeanHandler<Category>(Category.class), cid);
        return category;
    }
    // 根据分类id，查询该分类下的商品个数
    public int getCount(String cid) throws SQLException {
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        String sql = "select count(*) from product where cid = ?";
        Long count = qr.query(sql, new ScalarHandler<>(), cid);
        // ScalarHandler<>()默认返回Longleix
        return count.intValue();
    }

    // 查询指定分类id下的所有商品信息
    public List<Product> findProductByCid(String cid) throws SQLException {
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        String sql = "select * from product where cid = ?";
        List<Product> products = qr.query(sql, new BeanListHandler<Product>(Product.class), cid);
        return products;
    }
}
