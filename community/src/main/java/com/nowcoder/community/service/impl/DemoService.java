package com.nowcoder.community.service.impl;

import com.nowcoder.community.dao.DiscussPostMapper;
import com.nowcoder.community.dao.UserMapper;
import com.nowcoder.community.entity.DiscussPost;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.util.CommunityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DemoService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DiscussPostMapper discussPostMapper;

    /*
     * 传播机制: B called A 时
     * REQUIRED:  按B的事务来，如果B没有事务，则按A的事务来
     * REQUIRES_NEW: 按A的事务来
     * NESTED: 如果B存在事务，则A的事务嵌套在B的事务中执行
     *
     * */

    @Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRED)
    public Object save1() {
        // 新增用户
        User user = new User();
        user.setUsername("alpha");
        user.setSalt(CommunityUtil.generateUUID().substring(0, 5));
        user.setPassword(CommunityUtil.md5("123" + user.getSalt()));
        user.setEmail("alpha@qq.com");
        user.setHeaderUrl("images.nowcoder.com/head/1t.png");
        userMapper.insertSelective(user);
        // 新增帖子
        DiscussPost discussPost = new DiscussPost();
        // 这里会回填id
        discussPost.setUserId(user.getId());
        discussPost.setContent("新人报道");
        discussPost.setTitle("Hello");
        discussPostMapper.insertSelective(discussPost);
        Integer.valueOf("abc");
        return "OK";
    }

    public void jointPoint() {
        System.out.println("我是jointPoint，我执行了");
    }
}
