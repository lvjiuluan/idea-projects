package com.lagou.mapper;

import com.lagou.domain.User;

import java.util.List;

public interface UserMapper {
    // 关联查询所有用户，以及每个用户所关联的订单信息
    public List<User> findAllWithOrder();

    // 嵌套查询所有用户，以及每个用户所关联的订单信息
    public List<User> findAllWithOrder2();

    // 多对多关联查询
    public List<User> findAllWithRole();

    // 多对多关联嵌套查询 查询所有的用户，以及每个用户所具备的角色信息
    public List<User> findAllWithRole2();

    // 根据id查询用户
    public User findById(int id);
}
