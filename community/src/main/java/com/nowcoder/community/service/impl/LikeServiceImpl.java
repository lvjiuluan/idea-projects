package com.nowcoder.community.service.impl;

import com.nowcoder.community.service.ILikeService;
import com.nowcoder.community.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class LikeServiceImpl implements ILikeService {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void like(Integer userId, Integer entityType, Integer entityId) {
        String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType, entityId);
        /*
         * 如果当前用户已经给当前实体点赞
         * 则代表取消赞
         * 反之代表赞
         * */
        // 判断当前用户是否已经给当前实体点赞
        Boolean isMember = redisTemplate.opsForSet().isMember(entityLikeKey, userId);
        if (isMember) {
            // 已经点过赞了，要remove
            redisTemplate.opsForSet().remove(entityLikeKey, userId);
        } else {
            // 没点过赞，要加入
            redisTemplate.opsForSet().add(entityLikeKey, userId);
        }
        /*
         * 在java内存中是object
         * 在redis 是json字符串
         * */
    }

    @Override
    public Long findEntityLikeCount(Integer entityType, Integer entityId) {
        String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType, entityId);
        return redisTemplate.opsForSet().size(entityLikeKey);
    }

    @Override
    public Integer findEntityLikeStatus(Integer userId, Integer entityType, Integer entityId) {
        String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType, entityId);
        Boolean isMember = redisTemplate.opsForSet().isMember(entityLikeKey, userId);
        if (isMember) {
            return 1;
        }
        return 0;
    }

}
