package com.nowcoder.community.config;

import com.nowcoder.community.CommunityApplicationTest;
import com.nowcoder.community.service.IDiscussPostService;
import org.checkerframework.checker.units.qual.A;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class CaffeineConfigTest extends CommunityApplicationTest {

    @Autowired
    private CaffeineConfig caffeineConfig;

    @Autowired
    private IDiscussPostService discussPostService;

    @Test
    public void test() {
        CaffeineConfig.Post post = caffeineConfig.getPost();
        System.out.println(gson.toJson(post));
    }

    @Test
    public void testCache() {
        discussPostService.findDiscussPosts(0, 0, 10, 1);
        discussPostService.findDiscussPosts(0, 0, 10, 1);
        discussPostService.findDiscussPosts(0, 0, 10, 1);
        discussPostService.findDiscussPosts(0, 0, 10, 0);
    }

}