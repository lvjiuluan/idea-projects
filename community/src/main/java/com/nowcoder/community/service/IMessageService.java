package com.nowcoder.community.service;

import com.nowcoder.community.entity.Message;
import com.nowcoder.community.entity.Page;
import com.nowcoder.community.vo.ConversationVo;

import java.util.List;
import java.util.Map;

public interface IMessageService {
    // 分页查询所有的回话，每个会话包含一条最新的message
    Map<String, Object> findConversationByPage(Page page);

    // 分页查询【当前用户】的所有会话，每个会话包含一条最新的message
    List<Message> findCurrentUserConversations(Integer userId, Integer offset, Integer limit);

    // 查询【当前用户】的所有会话数量
    Integer findCurrentUserConversationCount(Integer userId);

    // 根据会话id分页查询当前会话的信息列表
    List<Message> findLetters(String conversationId, Integer offset, Integer limit);

    // 根据会话id查询当前会话的所有信息数量
    Integer findLetterCount(String conversationId);

    // 查询当前会话的未读消息活着当前用户的未读消息
    Integer findLetterUnderReadCount(Integer userId, String conversationId);

    // 新增一条消息
    Integer addMessage(Message message);
    // 把未读消息改为已读消息，修改私信状态

    Integer readMessage(String conversationId);
}
