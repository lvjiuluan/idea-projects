package com.nowcoder.community.service;

import com.nowcoder.community.entity.DiscussPost;
import com.nowcoder.community.entity.Page;

import java.util.List;
import java.util.Map;

public interface IDiscussPostService {

    List<DiscussPost> findDiscussPosts(Integer userId, Integer offset, Integer limit);

    Integer findDiscussPostRows(Integer userId);

    // 新增一条帖子
    Map<String, Object> addDiscussPost(DiscussPost discussPost);

    // 根据Id查询帖子和对应的用户信息
    Map<String, Object> findPostAndUserById(Integer id, Page page);

    // 根据postId查询帖子信息
    DiscussPost findDiscussPostById(Integer postId);

    // 根据postId置顶帖子 type = 1
    Map<String, Object> top(Integer postId);

    // 根据postId删除、status = 2
    Map<String, Object> delete(Integer postId);
    // 加精帖子 status = 1
    Map<String, Object> highlight(Integer postId);
}
