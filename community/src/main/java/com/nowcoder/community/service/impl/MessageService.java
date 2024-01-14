package com.nowcoder.community.service.impl;

import com.google.gson.Gson;
import com.nowcoder.community.dao.DiscussPostMapper;
import com.nowcoder.community.dao.MessageMapper;
import com.nowcoder.community.dao.UserMapper;
import com.nowcoder.community.entity.DiscussPost;
import com.nowcoder.community.entity.Message;
import com.nowcoder.community.entity.Page;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.service.IMessageService;
import com.nowcoder.community.util.HostHolder;
import com.nowcoder.community.util.SensitiveFilter;
import com.nowcoder.community.vo.ConversationVo;
import com.nowcoder.community.vo.MessageContentVo;
import com.nowcoder.community.vo.NoticeVo;
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
    @Autowired
    private SensitiveFilter sensitiveFilter;
    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private DiscussPostMapper discussPostMapper;

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

    @Override
    public List<Message> findCurrentUserConversations(Integer userId, Integer offset, Integer limit) {
        return messageMapper.selectConversations(userId, offset, limit);
    }

    @Override
    public Integer findCurrentUserConversationCount(Integer userId) {
        return messageMapper.selectConversationCount(userId);
    }

    @Override
    public List<Message> findLetters(String conversationId, Integer offset, Integer limit) {
        return messageMapper.selectLetters(conversationId, offset, limit);
    }

    @Override
    public Integer findLetterCount(String conversationId) {
        return messageMapper.selectLetterCount(conversationId);
    }

    @Override
    public Integer findLetterUnderReadCount(Integer userId, String conversationId) {
        return messageMapper.selectLetterUnreadCount(userId, conversationId);
    }

    @Override
    public Integer addMessage(Message message) {
        message.setContent(sensitiveFilter.replaceSensitiveWords(message.getContent()));
        return messageMapper.insertSelective(message);
    }

    @Override
    public Integer readMessage(String conversationId) {
        List<Message> messages = messageMapper.selectLetters(conversationId, 0, Integer.MAX_VALUE);
        // 筛选出toId是自己的Message
        List<Message> messageList = new ArrayList<>();
        User user = hostHolder.getUser();
        for (Message message : messages) {
            if (message.getToId() == user.getId() && message.getStatus() == 0) {
                messageList.add(message);
            }
        }
        if (messageList.size() == 0) return 0;// 全部已读，不用改状态
        return messageMapper.updateStatusByMessageList(messageList, 1);
    }

    @Override
    public Integer findUnreadNoticeCount(Integer userId, String conversationId) {
        return messageMapper.selectUnreadNoticeCount(userId, conversationId);
    }

    @Override
    public NoticeVo findNewestNotice(Integer userId, String conversationId) {
        List<Message> messageList = messageMapper.selectNoticeByPage(userId, conversationId, 0, 1);
        List<NoticeVo> noticeVoList = buidldNoticeVoList(messageList);
        if (!noticeVoList.isEmpty()) return noticeVoList.get(0);
        return null;
    }

    @Override
    public List<NoticeVo> findNoticeByPage(Integer userId, String conversationId, Page page) {
        page.setRows(messageMapper.selectNoticeRows(userId, conversationId));
        List<Message> messageList = messageMapper.selectNoticeByPage(userId,
                conversationId,
                page.getOffset(),
                page.getPageSize());
        List<NoticeVo> noticeVoList = buidldNoticeVoList(messageList);
        return noticeVoList;
    }

    @Override
    public Integer findNoticeCount(Integer userId, String conversationId) {
        return messageMapper.selectNoticeRows(userId, conversationId);
    }

    private List<NoticeVo> buidldNoticeVoList(List<Message> messageList) {
        List<NoticeVo> noticeVoList = new ArrayList<>();
        if (messageList == null || messageList.isEmpty()) return noticeVoList;
        for (Message message : messageList) {
            NoticeVo noticeVo = new NoticeVo();
            noticeVo.setMessage(message);
            MessageContentVo messageContentVo = new Gson().fromJson(message.getContent(), MessageContentVo.class);
            noticeVo.setUser(userMapper.selectByPrimaryKey(messageContentVo.getUserId()));
            noticeVo.setDiscussPost(discussPostMapper.selectByPrimaryKey(messageContentVo.getPostId()));
            noticeVoList.add(noticeVo);
        }
        return noticeVoList;
    }
    /*
     * 用户 【xxx】 评论/点赞/关注 了你的帖子/空,点击查看【帖子id】
     *
     *
     * */
}
