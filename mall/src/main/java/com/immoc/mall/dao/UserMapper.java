package com.immoc.mall.dao;

import com.immoc.mall.pojo.User;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User row);

    int insertSelective(User row);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User row);

    int updateByPrimaryKey(User row);

    int countByUsername(String username);

    int countByEmail(String email);

    User selectByUsername(String username);
}