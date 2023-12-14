package com.lagou.dao;

import com.lagou.domain.Account;

import java.util.List;

public interface AccountDao {
    // 查询所有
    public List<Account> findAll();

    // 根据id查询
    public Account findById(int id);

    // 添加账户
    public void save(Account account);

    // 修改账户
    public void update(Account account);

    // 删除账户
    public void delete(int id);
}
