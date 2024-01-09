package com.nowcoder.community.service.impl;

import com.nowcoder.community.dao.MessageMapper;
import com.nowcoder.community.dao.UserMapper;
import com.nowcoder.community.entity.Message;
import com.nowcoder.community.entity.Page;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.service.IMessageService;
import com.nowcoder.community.vo.ConversationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MessageService implements IMessageService {

    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public Map<String, Object> findConversationByPage(Page page) {
        Map<String, Object> map = new HashMap<>();
        // 查询出会话有多少行
        Integer rows = messageMapper.selectConversationRows();
        // 设置page参数
        page.setRows(rows);
        // 分页查询会话id 会话id没有按时间排序，安装id本身排序
        List<String> conversationIdList = messageMapper.selectConversationId(page.getOffset(), page.getPageSize());
        // 根据conversationIdList查询出所有的Message,结果已根据时间排序
        // 最新的会话会显示在最前面
        List<Message> messageList = messageMapper.selectMessgByConversationIdList(conversationIdList);
        // 用messageMap保留每个conversation_id对应的最新的message
        Map<String, Message> messageMap = new HashMap<>();
        for (Message message : messageList) {
            Message newestMessage = messageMap.get(message.getConversationId());
            if (newestMessage == null) {
                map.put(message.getConversationId(), message);
            }
        }
        // 构造ConversationVo
        List<ConversationVo> conversationVoList = new ArrayList<>();
        for (String conversationId : messageMap.keySet()) {
            Message newestMessage = messageMap.get(conversationId);
            // 根据fromId和toId将user查询出来
            User fromUser = userMapper.selectByPrimaryKey(newestMessage.getFromId());
            User toUser = userMapper.selectByPrimaryKey(newestMessage.getToId());
            ConversationVo conversationVo = new ConversationVo(conversationId, newestMessage, fromUser, toUser);
            conversationVoList.add(conversationVo);

        }
        map.put("page", page);
        map.put("conversationVoList", conversationVoList);
        return map;
    }
}
