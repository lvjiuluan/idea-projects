package com.lagou.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/***
 *  从数据源获取一个连接的工具类
 */
@Component
public class ConnectionUtils {
    @Autowired
    private DataSource dataSource;

    private ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    public Connection getThreadConnection() {
        // 先从threadLocal中获取连接
        // 同一个线程的connection相同
        Connection connection = threadLocal.get();
        if (connection == null) {
            try {
                connection = dataSource.getConnection();
                threadLocal.set(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    /**
     * 解除当前线程绑定的连接
     */
    public void removeThreadConnection() {
        threadLocal.remove();
    }
}
