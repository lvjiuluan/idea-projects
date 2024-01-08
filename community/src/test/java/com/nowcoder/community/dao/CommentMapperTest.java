package com.nowcoder.community.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nowcoder.community.CommunityApplicationTest;
import com.nowcoder.community.entity.Comment;
import com.nowcoder.community.entity.Page;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

public class CommentMapperTest extends CommunityApplicationTest {

    @Autowired
    private CommentMapper commentMapper;

    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Test
    public void selectCommentListByTargetId() {
        Integer rows = commentMapper.selectTotalRowsByEntity(1, 275);
        System.out.println(rows);
        Page page = new Page();
        page.setRows(rows);
        List<Comment> comments = commentMapper.selectCommentListByEntity(1, 275, page.getOffset(), page.getPageSize());
        System.out.println(gson.toJson(comments));
    }

    @Test
    public void selectCommentListByEntityId() {

    }
}