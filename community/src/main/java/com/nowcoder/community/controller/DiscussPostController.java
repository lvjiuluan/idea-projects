package com.nowcoder.community.controller;

import com.nowcoder.community.annotation.LoginRequired;
import com.nowcoder.community.entity.DiscussPost;
import com.nowcoder.community.entity.Page;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.service.IDiscussPostService;
import com.nowcoder.community.service.ILikeService;
import com.nowcoder.community.service.IMessageService;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * 尽量保持一致
 * */
@Controller
public class DiscussPostController {
    @Autowired
    private IDiscussPostService discussPostService;
    @Autowired
    private IUserService userService;
    @Autowired
    private ILikeService likeService;
    @Autowired
    private IMessageService messageService;
    @Autowired
    private HostHolder hostHolder;

    @GetMapping("/index")
    public String getIndexPage(Model model, Page page) {
        page.setRows(discussPostService.findDiscussPostRows(0));
        page.setPath("/index"); // 页面就可以复用这个路径了
        List<DiscussPost> discussPostList = discussPostService.findDiscussPosts(0, page.getOffset(), page.getPageSize());
        List<Map<String, Object>> discussPosts = new ArrayList<>();
        if (discussPosts != null) {
            for (DiscussPost discussPost : discussPostList) {
                Map<String, Object> map = new HashMap<>();
                map.put("post", discussPost);
                User user = userService.findUserById(discussPost.getUserId());
                map.put("user", user);
                // 加上该帖子的点赞数量
                Long likeCount = likeService.findEntityLikeCount(1, discussPost.getId());
                map.put("likeCount", likeCount);
                discussPosts.add(map);
            }
        }
        model.addAttribute("discussPosts", discussPosts);


        // page会自动注入给model
        return "index";
    }

    @PostMapping("/post/add")
    @ResponseBody
    public String addDiscussPost(DiscussPost discussPost) {
        Map<String, Object> map = discussPostService.addDiscussPost(discussPost);
        String response;
        if (map.isEmpty()) {
            response = CommunityUtil.getJSONString(0, "发送帖子成功！ ");
        } else {
            response = CommunityUtil.getJSONString(1, map);
        }
        return response;
    }

    @GetMapping("/post/detail/{id}")
    public String detail(@PathVariable Integer id, Model model, Page page) {
        // 由于page是对象，会自动封装到model中
        page.setPath("/post/detail/" + id);
        page.setPageSize(5);
        Map<String, Object> map = discussPostService.findPostAndUserById(id, page);
        model.addAllAttributes(map);
        return "site/discuss-detail";
    }

    @GetMapping("/error")
    public String getErrorPage() {
        return "/error/500";
    }
}
