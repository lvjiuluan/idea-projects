package com.nowcoder.community.service.impl;

import com.nowcoder.community.CommunityApplicationTest;
import com.nowcoder.community.entity.Message;
import com.nowcoder.community.entity.Page;
import com.nowcoder.community.service.IMessageService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

import static com.nowcoder.community.constant.EventTopicsConst.COMMENT;
import static org.junit.Assert.*;

public class MessageServiceTest extends CommunityApplicationTest {

    @Autowired
    private IMessageService messageService;
    private Integer userId = 149;

    @Test
    public void findNoticeByPage() {

    }
    @Test
    public void findNewestNotice(){

    }

    @Test
    public void findConversationByPage() {
        Page page = new Page();
        page.setPageSize(2);
        Map<String, Object> map = messageService.findConversationByPage(page);
        System.out.println(gson.toJson(map));
    }
}