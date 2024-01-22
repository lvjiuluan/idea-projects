package com.nowcoder.community.controller;

import com.nowcoder.community.annotation.LoginRequired;
import com.nowcoder.community.entity.Event;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.enums.EntiyTypeEnum;
import com.nowcoder.community.event.EventProducer;
import com.nowcoder.community.service.ILikeService;
import com.nowcoder.community.util.CommunityUtil;
import com.nowcoder.community.util.HostHolder;
import com.nowcoder.community.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

import static com.nowcoder.community.constant.EventTopicsConst.LIKE;

@Controller
public class LikeController {
    @Autowired
    private ILikeService likeService;
    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private EventProducer eventProducer;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @PostMapping("/like")
    @ResponseBody
    public String like(Integer entityType, Integer entityId, Integer entityUserId, Integer postId) {
        User user = hostHolder.getUser();
        likeService.like(user.getId(), entityType, entityId, entityUserId);
        // 数量
        Long likeCount = likeService.findEntityLikeCount(entityType, entityId);
        // 状态
        Integer likeStatus = likeService.findEntityLikeStatus(user.getId(), entityType, entityId);
        Map<String, Object> map = new HashMap<>();
        map.put("likeCount", likeCount);
        map.put("likeStatus", likeStatus);
        /*
         * 触发点赞事件
         * */
        if (likeStatus == 1) {
            // 表示是点赞，去触发事件
            Event event = new Event()
                    .setTopic(LIKE)
                    .setUserId(hostHolder.getUser().getId())
                    .setEntityType(entityType)
                    .setEntityId(entityId)
                    .setEntityUserId(entityUserId)
                    .setData("postId", postId);
            eventProducer.fireEvent(event);
        }
        // 如果是对帖子点赞，才会去定时计算帖子分数
        if (EntiyTypeEnum.POST.getCode().equals(entityType)) {
            // 点赞 帖子 会把post存到cache中，定时计算分数
            String redisKey = RedisKeyUtil.getPostScoreKey();
            redisTemplate.opsForSet().add(redisKey, postId);
        }
        return CommunityUtil.getJSONString(0, map);
    }
}
