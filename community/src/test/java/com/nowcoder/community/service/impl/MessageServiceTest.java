package com.nowcoder.community.service.impl;

import com.nowcoder.community.CommunityApplicationTest;
import com.nowcoder.community.entity.Page;
import com.nowcoder.community.service.IMessageService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

import static org.junit.Assert.*;

public class MessageServiceTest extends CommunityApplicationTest {

    @Autowired
    private IMessageService messageService;

    @Test
    public void findConversationByPage() {
        Page page = new Page();
        page.setPageSize(2);
        Map<String, Object> map = messageService.findConversationByPage(page);
        System.out.println(gson.toJson(map));
    }
}