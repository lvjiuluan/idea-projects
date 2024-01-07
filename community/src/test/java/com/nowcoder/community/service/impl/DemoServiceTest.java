package com.nowcoder.community.service.impl;

import com.nowcoder.community.CommunityApplicationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class DemoServiceTest extends CommunityApplicationTest {

    @Autowired
    private DemoService demoService;

    @Test
    public void save1() {
        demoService.save1();
    }
}