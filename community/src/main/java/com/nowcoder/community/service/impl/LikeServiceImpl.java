package com.nowcoder.community.service.impl;

import com.nowcoder.community.dao.CommentMapper;
import com.nowcoder.community.dao.DiscussPostMapper;
import com.nowcoder.community.entity.Comment;
import com.nowcoder.community.entity.DiscussPost;
import com.nowcoder.community.enums.EntiyTypeEnum;
import com.nowcoder.community.service.ILikeService;
import com.nowcoder.community.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

@Service
public class LikeServiceImpl implements ILikeService {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public void like(Integer userId,
                     Integer entityType,
                     Integer entityId,
                     Integer entityUserId) {
        /*
         *
         * 这里涉及到两个地方值的修改，所以需要用到事务
         * redis的事务最好用编程式事务
         * 在redis里面就不要再去操作数据库了
         * */
        redisTemplate.execute(new SessionCallback() {

            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                // 1 拼好key
                String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType, entityId);
                String targetUserLikeKey = RedisKeyUtil.getUserLikeKey(entityUserId);
                Boolean isMember = redisTemplate.opsForSet().isMember(entityLikeKey, userId);
                // 查询之后再开启事务，否则在事务之中是查询不了的
                operations.multi();
                // 在中间执行两次修改操作
                if (isMember) {
                    /*
                     * Redis 的 INCR 操作用于将存储在指定键（key）
                     * 上的数值增加 1。如果该键不存在，
                     * Redis 会在执行 INCR 操作前先将其设置为 0！！！
                     * 所以不要判断key是否存在
                     * */
                    redisTemplate.opsForSet().remove(entityLikeKey, userId);
                    redisTemplate.opsForValue().decrement(targetUserLikeKey);
                } else {
                    redisTemplate.opsForSet().add(entityLikeKey, userId);
                    redisTemplate.opsForValue().increment(targetUserLikeKey);
                }
                return operations.exec();
            }
        });
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

    @Override
    public Integer findUserLikeCount(Integer userId) {
        String userLikeKey = RedisKeyUtil.getUserLikeKey(userId);
        Object obj = redisTemplate.opsForValue().get(userLikeKey);
        Integer userLikeCount = (Integer) obj;
        return userLikeCount == null ? Integer.valueOf(0) : userLikeCount;
    }

}
