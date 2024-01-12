package com.nowcoder.community.service;

import com.nowcoder.community.entity.Page;

import java.util.Map;

public interface IFollowService {
    // 关注方法
    void follow(Integer userId, Integer entityType, Integer entityId);

    // 取消关注方法
    void unfollow(Integer userId, Integer entityType, Integer entityId);

    // 查询某个用户 关注实体的数量
    Long findFolloweeCount(Integer userId, Integer entityType);

    // 查询某个实体 粉丝的数量
    Long findFollowerCount(Integer entityType, Integer entityId);

    // 判断当前用户是否已关注该目标实体
    Boolean hasFollowed(Integer userId, Integer entityType, Integer entityId);

    // 分页查询 关注 某个实体 的粉丝 有哪些人
    Map<String, Object> findFollowerByPage(Integer entityType, Integer entityId, Page page);

    // 分页查询 某个人 关注的 某类 实体有哪些
    Map<String, Object> findFolloweeByPage(Integer userId, Integer entityType, Page page);
}
