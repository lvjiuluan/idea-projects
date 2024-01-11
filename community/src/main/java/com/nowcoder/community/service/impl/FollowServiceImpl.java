package com.nowcoder.community.service.impl;

import com.nowcoder.community.entity.Page;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.enums.EntiyTypeEnum;
import com.nowcoder.community.service.IFollowService;
import com.nowcoder.community.service.IUserService;
import com.nowcoder.community.util.HostHolder;
import com.nowcoder.community.util.RedisKeyUtil;
import com.nowcoder.community.vo.UserFollowVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FollowServiceImpl implements IFollowService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private IUserService userService;
    @Autowired
    private HostHolder hostHolder;

    @Override
    public void follow(Integer userId, Integer entityType, Integer entityId) {
        // 存两份数据，需要用到事务操作
        redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations redisOperations) throws DataAccessException {
                // 关注动作会产生两个key
                String followeeKey = RedisKeyUtil.getFolloweeKey(userId, entityType);
                String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityId);
                // 连续做两次存储操作 在事务中
                redisOperations.multi();
                // 用户关注的实体
                redisOperations.opsForZSet().add(followeeKey, entityId, System.currentTimeMillis());
                // 实体拥有的粉丝
                redisOperations.opsForZSet().add(followerKey, userId, System.currentTimeMillis());
                return redisOperations.exec();
            }
        });
    }

    @Override
    public void unfollow(Integer userId, Integer entityType, Integer entityId) {
        // 存两份数据，需要用到事务操作
        redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations redisOperations) throws DataAccessException {
                // 关注动作会产生两个key
                String followeeKey = RedisKeyUtil.getFolloweeKey(userId, entityType);
                String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityId);
                // 连续做两次存储操作 在事务中
                redisOperations.multi();
                // 用户关注的实体
                redisOperations.opsForZSet().remove(followeeKey, entityId);
                // 实体拥有的粉丝
                redisOperations.opsForZSet().remove(followerKey, userId);
                return redisOperations.exec();
            }
        });
    }

    @Override
    public Long findFolloweeCount(Integer userId, Integer entityType) {
        // 1 构造key
        String followeeKey = RedisKeyUtil.getFolloweeKey(userId, entityType);
        return redisTemplate.opsForZSet().zCard(followeeKey);
    }

    @Override
    public Long findFollowerCount(Integer entityType, Integer entityId) {
        String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityId);
        return redisTemplate.opsForZSet().zCard(followerKey);
    }

    @Override
    public Boolean hasFollowed(Integer userId, Integer entityType, Integer entityId) {
        String followeeKey = RedisKeyUtil.getFolloweeKey(userId, entityType);
        // 判断userId entityType key中是否有entityId这个值value
        return redisTemplate.opsForZSet().score(followeeKey, entityId) != null;
    }

    @Override
    public Map<String, Object> findFollowerByPage(Integer entityType, Integer entityId, Page page) {
        Map<String, Object> map = new HashMap<>();
        // 查询 该 实体 的粉丝一共有多少行
        page.setRows(this.findFollowerCount(entityType, entityId).intValue());
        // 构造key
        String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityId);
        // 分页 查询 实体 的粉丝有哪些，按关注的时间有小到大
        Set<ZSetOperations.TypedTuple<Object>> typedTuples = redisTemplate.opsForZSet().rangeByScoreWithScores(followerKey,
                Double.MIN_VALUE,
                Double.MAX_VALUE,
                page.getOffset(),
                page.getPageSize());
        List<Integer> entityIdList = new ArrayList<>();
        List<Long> scores = new ArrayList<>();
        for (ZSetOperations.TypedTuple<Object> typedTuple : typedTuples) {
            Integer id = (Integer) typedTuple.getValue();
            Long score = typedTuple.getScore().longValue();
            entityIdList.add(id);
            scores.add(score);

        }
        // 构造UserFollowVo对象
        List<UserFollowVo> userFollowVoList = new ArrayList<>();

        if (!entityIdList.isEmpty()) {
            // 不为空，去查对应的实体
            // 根据 entityType 调用对应的servvice
            if (EntiyTypeEnum.USER.getCode().equals(entityType)) {
                // 用户类型，根据idList查询出所有的user
                List<User> userList = userService.findUsersByIdList(entityIdList);

                for (int i = 0; i < userList.size(); i++) {
                    User user = userList.get(i);
                    Long score = scores.get(i);
                    UserFollowVo userFollowVo = new UserFollowVo();
                    BeanUtils.copyProperties(user, userFollowVo);
                    userFollowVo.setFollowTime(new Date(score));
                    // 判断当前登录用户是否关注了目标
                    Boolean hasFollowed = this.hasFollowed(hostHolder.getUser().getId(),
                            EntiyTypeEnum.USER.getCode(),
                            user.getId());
                    userFollowVo.setHasFollowed(hasFollowed);
                    userFollowVoList.add(userFollowVo);
                }
            }
        }
        map.put("userFollowVoList", userFollowVoList);
        return map;
    }
}
