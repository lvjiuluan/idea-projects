package com.nowcoder.community.controller;

import com.nowcoder.community.entity.Message;
import com.nowcoder.community.entity.Page;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.service.IMessageService;
import com.nowcoder.community.service.IUserService;
import com.nowcoder.community.util.CommunityUtil;
import com.nowcoder.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MessageController {

    @Autowired
    private IMessageService messageService;
    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private IUserService userService;

    @GetMapping("/letter/list")
    public String letter(Model model, Page page) {
//        Integer.valueOf("acv");
        // 分页信息
        page.setPageSize(5);
        page.setPath("/letter/list");
        User user = hostHolder.getUser();
        page.setRows(messageService.findCurrentUserConversationCount(user.getId()));
        List<Message> conversationList = messageService.findCurrentUserConversations(user.getId(),
                page.getOffset(),
                page.getPageSize());
        List<Map<String, Object>> conversations = new ArrayList<>();
        if (conversationList != null) {
            for (Message message : conversationList) {
                Map<String, Object> map = new HashMap<>();
                map.put("message", message);
                map.put("letterCount", messageService.findLetterCount(message.getConversationId()));
                map.put("unreadCount", messageService.findLetterUnderReadCount(user.getId(),
                        message.getConversationId()));

                Integer target_id = user.getId() == message.getFromId() ? message.getToId() : message.getFromId();
                map.put("target", userService.findUserById(target_id));
                conversations.add(map);
            }
        }
        model.addAttribute("conversations", conversations);
        model.addAttribute("letterUnreadCount", messageService.findLetterUnderReadCount(user.getId(), ""));
        return "site/letter";
    }

    /*
     *
     * TODO: 1 @{|a/b/${id}|}  @里面不可以直接写${},回把值渲染进去, 需要加||渲染
     *     "@{|/letter/detail/${conversation.message.conversationId}|}"
     *
     *
     * * */
    @GetMapping("/letter/detail/{conversationId}")
    public String letter(Model model, Page page, @PathVariable String conversationId) {
        page.setPageSize(5);
        page.setPath("/letter/detail/" + conversationId);
        page.setRows(messageService.findLetterCount(conversationId));
        List<Message> messageList = messageService.findLetters(conversationId,
                page.getOffset(),
                page.getPageSize());
        User user = hostHolder.getUser();
        List<Map<String, Object>> letters = new ArrayList<>();
        if (messageList != null) {
            Integer targetId = messageList.get(0).getToId() == user.getId()
                    ? messageList.get(0).getFromId() : messageList.get(0).getToId();
            model.addAttribute("target", userService.findUserById(targetId));
            for (Message message : messageList) {
                Map<String, Object> map = new HashMap<>();
                map.put("message", message);
                map.put("fromUser", userService.findUserById(message.getFromId()));
                letters.add(map);
            }
            model.addAttribute("letters", letters);
        }
        // 在返回前要将该会话id所有的message设置为已读状态
        Integer rows = messageService.readMessage(conversationId);
        return "site/letter-detail";
    }

    @PostMapping("/letter/send")
    @ResponseBody
    public String sendLetter(String toName, String content) {
        /*
         * 跟着做，学习代码风格，思维
         * */
//        Integer.valueOf("acv");
        User target = userService.findUserByUsername(toName);
        if (target == null) {
            return CommunityUtil.getJSONString(1, "目标用户不存在");
        }
        Message message = new Message();
        User user = hostHolder.getUser();
        message.setFromId(user.getId());
        message.setToId(target.getId());
        if (message.getFromId() < message.getToId()) {
            message.setConversationId(message.getFromId() + "_" + message.getToId());
        } else {
            message.setConversationId(message.getToId() + "_" + message.getFromId());
        }
        message.setContent(content);
        messageService.addMessage(message);
        return CommunityUtil.getJSONString(0, "发送消息成功");
    }
}
