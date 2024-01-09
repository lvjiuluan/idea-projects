package com.nowcoder.community.dao;

import com.nowcoder.community.CommunityApplicationTest;
import com.nowcoder.community.entity.Message;
import com.nowcoder.community.entity.Page;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MessageMapperTest extends CommunityApplicationTest {

    @Autowired
    private MessageMapper messageMapper;

    @Test
    public void selectConversationIdSet() {
        List<Message> allConversationMessgae = messageMapper.selectAllConversation();
    }

    @Test
    public void selectConverstaionMessageId() {
        Page page = new Page();
        Integer rows = messageMapper.selectConversationRows();
        System.out.println(rows);
        page.setRows(rows);
        page.setPageSize(2);
        List<String> conversationIdList = messageMapper.selectConversationId(page.getOffset(), page.getPageSize());
        System.out.println(conversationIdList.size());
        // 根据conversationIdList查询出所有的Message
        List<Message> messageList = messageMapper.selectMessgByConversationIdList(conversationIdList);
//        System.out.println(gson.toJson(messageList));
        Map<String, Message> map = new HashMap<>();
        for (Message message : messageList) {
            Message newestMessage = map.get(message.getConversationId());
            if (newestMessage == null) {
                map.put(message.getConversationId(), message);
            }
        }
        System.out.println(gson.toJson(map));

    }
}