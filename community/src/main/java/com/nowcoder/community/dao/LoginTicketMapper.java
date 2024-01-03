package com.nowcoder.community.dao;

import com.nowcoder.community.entity.LoginTicket;

public interface LoginTicketMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LoginTicket row);

    int insertSelective(LoginTicket row);

    LoginTicket selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LoginTicket row);

    int updateByPrimaryKey(LoginTicket row);
}