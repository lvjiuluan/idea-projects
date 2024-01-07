package com.nowcoder.community.controller;

import com.nowcoder.community.form.AddCommentForm;
import com.nowcoder.community.service.ICommentService;
import com.nowcoder.community.util.CommunityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private ICommentService commentService;

    @PostMapping("/add")
    @ResponseBody
    public String add(AddCommentForm form) {
        Map<String, Object> map = commentService.addComment(form);
        if (!map.isEmpty()) {
            return CommunityUtil.getJSONString(1, map);
        }
        return CommunityUtil.getJSONString(0, "新增帖子成功");
    }
}
