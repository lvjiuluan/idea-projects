package com.nowcoder.community.service;

import com.nowcoder.community.entity.Page;
import com.nowcoder.community.vo.ConversationVo;

import java.util.List;
import java.util.Map;

public interface IMessageService {
    // 分页查询所有的回话，每个会话包含一条最新的message
    Map<String, Object> findConversationByPage(Page page);
}
