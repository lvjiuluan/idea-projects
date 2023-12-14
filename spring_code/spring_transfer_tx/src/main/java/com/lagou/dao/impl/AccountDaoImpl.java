package com.lagou.dao.impl;

import com.lagou.dao.AccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AccountDaoImpl implements AccountDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void out(String outUser, double money) {
        String sql = "update account set money=money-? where name=?";
        jdbcTemplate.update(sql, money, outUser);
    }

    @Override
    public void in(String inUser, double money) {
        String sql = "update account set money=money+? where name=?";
        jdbcTemplate.update(sql, money, inUser);
    }
}
