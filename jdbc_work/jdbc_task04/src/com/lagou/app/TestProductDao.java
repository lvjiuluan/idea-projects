package com.lagou.app;

import com.lagou.dao.ProductDao;
import com.lagou.entity.Product;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

public class TestProductDao {
    ProductDao productDao = new ProductDao();
    @Test
    public void testFindProductById() throws SQLException {
        Product product = productDao.findProductById("1");
        System.out.println(product+"\n"+product.getCategory());

    }
    // 查询分类id为3的分类下有几个商品
    @Test
    public void testGetCount() throws SQLException {
        int count = productDao.getCount("3");
        System.out.println(count);
    }
    // 测试查询指定分类id下的所有商品信息
    @Test
    public void testfindProductByCid() throws SQLException {
        List<Product> products = productDao.findProductByCid("2");
        for (Product product : products) {
            System.out.println(product);
        }

    }
}
