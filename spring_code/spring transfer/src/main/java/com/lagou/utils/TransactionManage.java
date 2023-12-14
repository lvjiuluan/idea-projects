package com.lagou.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 事务管理器工具类
 */
@Component
public class TransactionManage {
    @Autowired
    private ConnectionUtils connectionUtils;

    // 开启事务
    public void beginTransaction() {
        Connection connection = connectionUtils.getThreadConnection();
        try {
            // 开启了手动提交事务
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 提交事务
    public void commit() {
        Connection connection = connectionUtils.getThreadConnection();
        try {
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 回滚事务
    public void rollback() {
        Connection connection = connectionUtils.getThreadConnection();
        try {
            connection.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 释放资源
    public void release() {
        try {
            Connection connection = connectionUtils.getThreadConnection();
            // 将手动事务设置为自动
            connection.setAutoCommit(true);
            // 归还连接
            connection.close();
            // 解除绑定
            connectionUtils.removeThreadConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
