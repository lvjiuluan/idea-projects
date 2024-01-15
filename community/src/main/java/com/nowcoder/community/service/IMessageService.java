package com.nowcoder.community.service;

import com.nowcoder.community.entity.Message;
import com.nowcoder.community.entity.Page;
import com.nowcoder.community.vo.ConversationVo;
import com.nowcoder.community.vo.NoticeVo;

import java.util.List;
import java.util.Map;

public interface IMessageService {
    // 分页查询所有的回话，每个会话包含一条最新的message
    Map<String, Object> findConversationByPage(Page page);

    // 分页查询【某个用户】的所有会话，每个会话包含一条最新的message
    List<Message> findCurrentUserConversations(Integer userId, Integer offset, Integer limit);

    // 查询【某个用户】的所有会话数量
    Integer findCurrentUserConversationCount(Integer userId);

    // 根据会话id分页查询当前会话的信息列表
    List<Message> findLetters(String conversationId, Integer offset, Integer limit);

    // 根据会话id查询当前会话的所有信息数量
    Integer findLetterCount(String conversationId);

    // 查询【某个用户】会话的未读消息或者当前用户的未读消息(当conversationId为null时)
    Integer findLetterUnderReadCount(Integer userId, String conversationId);

    // 新增一条消息
    Integer addMessage(Message message);

    // 把未读消息改为已读消息，修改私信状态
    Integer readMessage(String conversationId);

    // 查询【某个用户】某个conversationId下的未读通知数量，或者所有的未读通知(conversationId为空时)
    Integer findUnreadNoticeCount(Integer userId, String conversationId);

    // 查询某个用户的某个conversationId下最新的一条通知 offset = 0, limit = 1
    NoticeVo findNewestNotice(Integer userId, String conversationId);

    // 分页查询某个用户的某个conversationId下的通知
    List<NoticeVo> findNoticeByPage(Integer userId, String conversationId, Page page);

    // 查询某个用户，某个会话所有通知数量
    Integer findNoticeCount(Integer userId, String conversationId);

    // 阅读通知
    Integer readNotice(Integer userId,String conversationId);
}
