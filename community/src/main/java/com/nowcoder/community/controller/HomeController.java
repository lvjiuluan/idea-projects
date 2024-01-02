package com.nowcoder.community.controller;

import com.nowcoder.community.entity.DiscussPost;
import com.nowcoder.community.entity.Page;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.service.IDiscussPostService;
import com.nowcoder.community.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * 尽量保持一致
 * */
@Controller
public class HomeController {
    @Autowired
    private IDiscussPostService discussPostService;
    @Autowired
    private IUserService userService;

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
                discussPosts.add(map);
            }
        }
        model.addAttribute("discussPosts", discussPosts);
        // page会自动注入给model
        return "index";
    }
}
