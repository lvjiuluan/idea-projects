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
}
