package com.lagou.dao.impl;

import com.lagou.dao.AccountDao;
import com.lagou.domain.Account;
import com.lagou.utils.ConnectionUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Repository("accountDao")
public class AccountDaoImpl implements AccountDao {
    @Autowired
    private QueryRunner queryRunner;
    @Autowired
    private ConnectionUtils connectionUtils;

    @Override
    public List<Account> findAll() {
        return null;
    }

    @Override
    public Account findById(int id) {
        return null;
    }

    @Override
    public void save(Account account) {

    }

    @Override
    public void update(Account account) {

    }

    @Override
    public void delete(int id) {

    }

    /**
     * 在queryRunner.update方法中可以传入connection
     * 保证in方法和out方法用到 connection一样
     *
     * @param outUser
     * @param money
     */
    @Override
    public void out(String outUser, double money) {
        try {
            Connection connection = connectionUtils.getThreadConnection();
            String sql = "update account set money = money - ? where name = ?";
            queryRunner.update(connection,sql, money, outUser);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void in(String inUser, double money) {
        try {
            Connection connection = connectionUtils.getThreadConnection();
            String sql = "update account set money = money + ? where name = ?";
            queryRunner.update(connection,sql, money, inUser);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
