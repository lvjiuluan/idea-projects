package com.nowcoder.community.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nowcoder.community.CommunityApplicationTest;
import com.nowcoder.community.service.ICommentService;
import com.nowcoder.community.vo.CommentVo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

public class CommentServiceImplTest extends CommunityApplicationTest {

    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Autowired
    private ICommentService commentService;

    @Test
    public void findCommentsById() {
        List<CommentVo> commentVoList = commentService.findCommentsById(12);
        System.out.println(gson.toJson(commentVoList));
    }
}