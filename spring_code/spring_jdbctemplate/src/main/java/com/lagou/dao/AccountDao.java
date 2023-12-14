package com.lagou.dao;

import com.lagou.domain.Account;

import java.util.List;

public interface AccountDao {
    // 查询所有账户
    public List<Account> findAll();

    // 根据id查询账户
    public Account findById(int id);

    // 添加
    public void save(Account account);

    // 更新
    public void update(Account account);

    // 根据id删除账户
    public void delete(int id);
}
