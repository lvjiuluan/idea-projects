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
        // 根据conversationIdList查询出所有的Message,结果根据时间排序
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

    @Test
    public void testMessage() {
        /*
        *
        *
        *
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
        * */
        Integer userId = 111;
        String conversationId = "111_112";
        Page page = new Page();
        page.setPageSize(15);
        page.setRows(messageMapper.selectConversationCount(userId));
        Integer rows = page.getRows();
        System.out.println(rows);
        List<Message> messageList = messageMapper.selectConversations(userId, page.getOffset(), page.getPageSize());
        System.out.println(gson.toJson(messageList));
        page.setRows(messageMapper.selectLetterCount(conversationId));
        rows = page.getRows();
        System.out.println(rows);
        List<Message> messageList1 = messageMapper.selectLetters(conversationId, page.getOffset(), page.getPageSize());
        System.out.println(gson.toJson(messageList1));
        Integer count = messageMapper.selectLetterUnreadCount(userId, "");
        System.out.println(count);
    }

    @Test
    public void updateStatusByMessageList() {
        List<Message> messageList = messageMapper.selectLetters("111_115", 0, Integer.MAX_VALUE);
        System.out.println(messageList.size());
        Integer rows = messageMapper.updateStatusByMessageList(messageList, 1);
        System.out.println(rows);

    }
}