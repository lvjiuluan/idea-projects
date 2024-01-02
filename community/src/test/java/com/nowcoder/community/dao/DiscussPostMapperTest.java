package com.nowcoder.community.dao;

import com.nowcoder.community.CommunityApplicationTest;
import com.nowcoder.community.entity.DiscussPost;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class DiscussPostMapperTest extends CommunityApplicationTest {

    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Test
    public void selectDiscussPosts() {
        List<DiscussPost> discussPostList = discussPostMapper.selectDiscussPosts(101, 1, 2);
        for (DiscussPost discussPost : discussPostList) {
            System.out.println(discussPost);
        }
        Integer rows = discussPostMapper.selectDiscussPostRows(101);
        System.out.println("总行数为: " + rows);
    }
}