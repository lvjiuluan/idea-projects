package com.lagou.app;

import com.lagou.dao.OdersDao;
import com.lagou.entity.Orders;
import com.lagou.entity.Product;
import org.junit.Test;

import java.sql.SQLException;

public class TestOdersDao {
    OdersDao ordersDao = new OdersDao();
    @Test
    public void testfindAllOrdersByUid() throws SQLException {
        for (Orders orders : ordersDao.findAllOrdersByUid("001")) {
            System.out.println(orders);
        }
    }
    @Test
    public void testfindAllProductByOid() throws SQLException {
        for (Product product : ordersDao.findAllProductByOid("order001")) {
            System.out.println(product);
        }

    }
}
