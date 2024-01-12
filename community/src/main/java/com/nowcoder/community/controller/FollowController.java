package com.nowcoder.community.controller;

import com.nowcoder.community.annotation.LoginRequired;
import com.nowcoder.community.entity.Page;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.enums.EntiyTypeEnum;
import com.nowcoder.community.service.IFollowService;
import com.nowcoder.community.service.IUserService;
import com.nowcoder.community.util.CommunityUtil;
import com.nowcoder.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class FollowController {

    @Autowired
    private IFollowService followService;
    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private IUserService userService;


    @PostMapping("/follow")
    @LoginRequired
    @ResponseBody
    public String follow(Integer entityType, Integer entityId) {
        User user = hostHolder.getUser();
        followService.follow(user.getId(), entityType, entityId);
        return CommunityUtil.getJSONString(0, "已关注");
    }

    @PostMapping("/unfollow")
    @LoginRequired
    @ResponseBody
    public String unfollow(Integer entityType, Integer entityId) {
        User user = hostHolder.getUser();
        followService.unfollow(user.getId(), entityType, entityId);
        return CommunityUtil.getJSONString(0, "已取消关注");
    }

    @GetMapping("/follower/{userId}")
    public String follower(Model model, @PathVariable Integer userId, Page page) {
        page.setPath("/follower/" + userId);
        page.setPageSize(5);
        User user = userService.findUserById(userId);
        model.addAttribute("user", user);
        Map<String, Object> map = followService.findFollowerByPage(EntiyTypeEnum.USER.getCode(), userId, page);
        model.addAllAttributes(map);
        return "site/follower";
    }
}
