package com.nowcoder.community.dao;

import com.nowcoder.community.entity.Message;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface MessageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Message row);

    int insertSelective(Message row);

    Message selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Message row);

    int updateByPrimaryKeyWithBLOBs(Message row);

    int updateByPrimaryKey(Message row);

    // 查询所有的对话消息
    List<Message> selectAllConversation();

    // 查询所有的通知消息
    List<Message> selectAllNotice();

    // 分页查询会话消息id
    List<String> selectConversationId(@Param("offset") Integer offset,
                                     @Param("limit") Integer limit);

    // 查询所有会话消息的总行数
    Integer selectConversationRows();

    // 根据conversationIdList查询出所有的Message
    List<Message> selectMessgByConversationIdList(@Param("conversationIdList") List<String> conversationIdList);

}