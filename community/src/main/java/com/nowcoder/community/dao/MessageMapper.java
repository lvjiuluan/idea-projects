package com.nowcoder.community.dao;

import com.nowcoder.community.entity.Message;

public interface MessageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Message row);

    int insertSelective(Message row);

    Message selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Message row);

    int updateByPrimaryKeyWithBLOBs(Message row);

    int updateByPrimaryKey(Message row);
}