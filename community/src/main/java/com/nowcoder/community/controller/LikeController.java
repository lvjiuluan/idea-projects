package com.nowcoder.community.controller;

import com.nowcoder.community.annotation.LoginRequired;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.service.ILikeService;
import com.nowcoder.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class LikeController {
    @Autowired
    private ILikeService likeService;
    @Autowired
    private HostHolder hostHolder;

    @PostMapping("/like")
    @ResponseBody
    public String like(Integer entityType, Integer entityId) {
        User user = hostHolder.getUser();
        likeService.like(user.getId(), entityType, entityId);
        // 数量
        Long entityLikeCount = likeService.findEntityLikeCount(entityType, entityId);
        // 状态
        Integer entityLikeStatus = likeService.findEntityLikeStatus(user.getId(), entityType, entityId);
        Map<String, Object> map = new HashMap<>();
        return null;
    }
}
