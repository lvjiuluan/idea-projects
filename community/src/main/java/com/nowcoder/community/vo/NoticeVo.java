package com.nowcoder.community.vo;

import com.nowcoder.community.entity.DiscussPost;
import com.nowcoder.community.entity.Message;
import com.nowcoder.community.entity.User;
import lombok.Data;

@Data
public class NoticeVo {
    private User user;
    private DiscussPost discussPost;
    private Message message;
    private String action;
    private String type;
    private String url;
}
