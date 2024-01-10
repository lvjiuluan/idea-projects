package com.nowcoder.community.service.impl;

import com.nowcoder.community.CommunityApplicationTest;
import com.nowcoder.community.aspect.DemoAspect;
import com.nowcoder.community.service.IMessageService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class DemoServiceTest extends CommunityApplicationTest {

    @Autowired
    private DemoService demoService;
//    @Autowired
//    private DemoAspect demoAspect;
    @Autowired
    private IMessageService messageService;

    @Test
    public void save1() {
        demoService.save1();
    }


    @Test
    public void jointPoint() {
        demoService.jointPoint();
    }
    @Test
    public void jointPoint1() {
        messageService.findCurrentUserConversationCount(111);
    }
}