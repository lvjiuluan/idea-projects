package com.nowcoder.community.service.impl;

import com.nowcoder.community.CommunityApplicationTest;
import com.nowcoder.community.entity.Page;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.enums.EntiyTypeEnum;
import com.nowcoder.community.service.IFollowService;
import com.nowcoder.community.service.IUserService;
import com.nowcoder.community.util.RedisKeyUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

public class FollowServiceImplTest extends CommunityApplicationTest {

    @Autowired
    private IFollowService followService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private IUserService userService;
    private Integer entityId = 11;

    @Test
    public void follow() {
        List<Integer> idList = IntStream.range(1, 151).boxed().collect(Collectors.toList());
        idList.remove(idList.indexOf(entityId));
        List<User> userIdList = userService.findUsersByIdList(idList);
        for (User user : userIdList) {
            followService.follow(user.getId(), EntiyTypeEnum.USER.getCode(), entityId);
        }
    }

    @Test
    public void findFollower() {
        Page page = new Page();
        Long rows = followService.findFollowerCount(EntiyTypeEnum.USER.getCode(), entityId);
        System.out.println(rows);
        page.setRows(rows.intValue());

        page.setPageSize(5);
        String followerKey = RedisKeyUtil.getFollowerKey(EntiyTypeEnum.USER.getCode(), entityId);
        System.out.println(followerKey);

        Set<ZSetOperations.TypedTuple<Object>> typedTuples = redisTemplate.opsForZSet().rangeByScoreWithScores(followerKey,
                Double.MIN_VALUE,
                Double.MAX_VALUE,
                page.getOffset(),
                page.getPageSize());
        List<Object> objects = new ArrayList<>();
        List<Long> scores = new ArrayList<>();
        for (ZSetOperations.TypedTuple<Object> typedTuple : typedTuples) {
            Object obj = typedTuple.getValue();
            Long score = typedTuple.getScore().longValue();
            objects.add(obj);
            scores.add(score);

        }
        List<Integer> userIdList = new ArrayList<>();
        for (Object object : objects) {
            userIdList.add((Integer) object);
        }
        System.out.println(gson.toJson(userIdList));
    }

    @Test
    public void findFollower02() {
        Page page = new Page();
        page.setPageSize(5);
        Map<String, Object> map = followService.findFollowerByPage(EntiyTypeEnum.USER.getCode(), entityId, page);
        System.out.println(gson.toJson(map));
    }

    @Test
    public void findFolloweeByPage(){
        Page page = new Page();
        page.setPageSize(5);
        Map<String, Object> map = followService.findFolloweeByPage(111, EntiyTypeEnum.USER.getCode(), page);
        System.out.println(gson.toJson(map));
    }
}