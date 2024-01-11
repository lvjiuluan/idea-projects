package com.nowcoder.community.service;

public interface ILikeService {
    // 点赞
    void like(Integer userId,
              Integer entityType,
              Integer entityId,
              Integer entityUserId);

    // 查询某实体点赞的数量
    Long findEntityLikeCount(Integer entityType, Integer entityId);

    // 查询某个用户是否对某个实体点赞状态 0-未点赞 1-点赞
    Integer findEntityLikeStatus(Integer userId, Integer entityType, Integer entityId);

    // 查询某个用户获得的所有点赞数量
    Integer findUserLikeCount(Integer userId);
}
