package com.lagou.dao;

import com.lagou.domain.Account;

import java.util.List;

public interface AccountDao {
    // 查询所有账户
    public List<Account> findAll();

    // 添加账户
    public void save(Account account);

    // 根据id查询账户
    public Account findById(Integer id);

    public void update(Account account);

    public void deleteBatch(Integer[] ids);
}
