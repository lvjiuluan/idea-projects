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

    //###################################################################

    // 分页查询当前用户的会话列表，针对每个会话，只返回一条最新的私信
    List<Message> selectConversations(@Param("userId") Integer userId,
                                      @Param("offset") Integer offset,
                                      @Param("limit") Integer limit);

    // 查询当前用户的会话数量，便于计算分页
    Integer selectConversationCount(Integer userId);

    // 根据会话id 查询某个会话所包含的私信列表
    List<Message> selectLetters(@Param("conversationId") String conversationId,
                                @Param("offset") Integer offset,
                                @Param("limit") Integer limit);

    // 根据会话id，查询某个会话所包含的私信数量
    Integer selectLetterCount(String conversationId);

    // 查询当前用户未读私信的数量，或当前用户某个会话的未读私信数量
    Integer selectLetterUnreadCount(@Param("userId") Integer userId,
                                    @Param("conversationId") String conversationId);
}