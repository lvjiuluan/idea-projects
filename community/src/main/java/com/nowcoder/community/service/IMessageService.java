package com.nowcoder.community.service;

public interface IMessageService {
    // 查询会话列表，每一个会话包含多个Message
    void findAllConversation();
}
