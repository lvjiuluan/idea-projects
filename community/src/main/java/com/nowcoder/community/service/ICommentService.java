package com.nowcoder.community.service;

import com.nowcoder.community.form.AddCommentForm;
import com.nowcoder.community.vo.CommentVo;

import java.util.List;
import java.util.Map;

public interface ICommentService {
    // 根据 comment-id 查询出所有的 子评论
    List<CommentVo> findCommentsById(Integer id);

    // 添加一条评论
    Map<String, Object> addComment(AddCommentForm form);
}
