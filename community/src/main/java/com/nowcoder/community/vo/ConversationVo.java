package com.nowcoder.community.vo;

import com.nowcoder.community.entity.Message;
import com.nowcoder.community.entity.User;
import lombok.Data;

import java.util.List;

@Data
public class ConversationVo {
    private String conversationId;
    private Message newestMessage;
    private List<Message> messageList;
    private User fromUser;
    private User toUser;

    public ConversationVo(String conversationId, Message newestMessage, User fromUser, User toUser) {
        this.conversationId = conversationId;
        this.newestMessage = newestMessage;
        this.fromUser = fromUser;
        this.toUser = toUser;
    }

    public ConversationVo() {
    }
}
