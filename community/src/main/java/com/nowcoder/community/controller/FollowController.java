package com.nowcoder.community.controller;

import com.nowcoder.community.annotation.LoginRequired;
import com.nowcoder.community.entity.Event;
import com.nowcoder.community.entity.Page;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.enums.EntiyTypeEnum;
import com.nowcoder.community.event.EventProducer;
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

import static com.nowcoder.community.constant.EventTopicsConst.FOLLOW;

@Controller
public class FollowController {

    @Autowired
    private IFollowService followService;
    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private IUserService userService;
    @Autowired
    private EventProducer eventProducer;


    @PostMapping("/follow")
    @LoginRequired
    @ResponseBody
    public String follow(Integer entityType, Integer entityId) {
        User user = hostHolder.getUser();
        followService.follow(user.getId(), entityType, entityId);
        /*触发关注事件
         * */
        Event event = new Event()
                .setTopic(FOLLOW)
                .setUserId(hostHolder.getUser().getId())
                .setEntityType(entityType)
                .setEntityId(entityId)
                .setEntityUserId(entityId);
        eventProducer.fireEvent(event);
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
        Boolean isFollowerPage = Boolean.TRUE;
        model.addAttribute("isFollowerPage", isFollowerPage);
        return "site/follower";
    }

    @GetMapping("/followee/{userId}")
    public String followee(Model model, @PathVariable Integer userId, Page page) {
        page.setPath("/followee/" + userId);
        page.setPageSize(5);
        User user = userService.findUserById(userId);
        model.addAttribute("user", user);
        Map<String, Object> map = followService.findFolloweeByPage(userId, EntiyTypeEnum.USER.getCode(), page);
        model.addAllAttributes(map);
        Boolean isFollowerPage = Boolean.FALSE;
        model.addAttribute("isFollowerPage", isFollowerPage);
        return "site/followee";
    }
}
