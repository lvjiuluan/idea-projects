package com.nowcoder.community.service.impl;

import com.nowcoder.community.dao.CommentMapper;
import com.nowcoder.community.dao.DiscussPostMapper;
import com.nowcoder.community.dao.UserMapper;
import com.nowcoder.community.entity.Comment;
import com.nowcoder.community.entity.DiscussPost;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.form.AddCommentForm;
import com.nowcoder.community.service.ICommentService;
import com.nowcoder.community.util.HostHolder;
import com.nowcoder.community.util.SensitiveFilter;
import com.nowcoder.community.vo.CommentVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommentServiceImpl implements ICommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private DiscussPostMapper discussPostMapper;
    @Autowired
    private SensitiveFilter sensitiveFilter;

    @Override
    public List<CommentVo> findCommentsById(Integer id) {
        List<Comment> comments = commentMapper.selectCommentListByEntity(2, id, null, null);
        if (comments == null) {
            return null;
        }
        // 构造CommentVo对象
        List<CommentVo> commentVoList = new ArrayList<>();
        for (Comment comment : comments) {
            CommentVo commentVo = new CommentVo();
            BeanUtils.copyProperties(comment, commentVo);
            User user = userMapper.selectByPrimaryKey(comment.getUserId());
            commentVo.setUser(user);
            if (!comment.getTargetId().equals(0)) {
                User targetUser = userMapper.selectByPrimaryKey(comment.getTargetId());
                commentVo.setTargetUser(targetUser);
            }
            commentVoList.add(commentVo);
        }
        return commentVoList;

    }

    @Override
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public Map<String, Object> addComment(AddCommentForm form) {
        Map<String, Object> map = new HashMap<>();
        // 内容不能为空
        if (form.getContent() == null) {
            map.put("msg", "内容不能为空！");
            return map;
        }

        Comment comment = new Comment();
        BeanUtils.copyProperties(form, comment);
        User user = hostHolder.getUser();
        comment.setUserId(user.getId());
        if (comment.getTargetId() == null) {
            comment.setTargetId(0);
        }
        // 对内容中的html标签处理
        // 对内容敏感词过滤

        comment.setContent(HtmlUtils.htmlEscape(comment.getContent()));
        comment.setContent(sensitiveFilter.replaceSensitiveWords(comment.getContent()));

        // 先增加帖子
        Integer rows1 = commentMapper.insertSelective(comment);
        if (rows1 < 0) {
            map.put("msg", "新增帖子失败");
            return map;
        }
        // 修改discussPost的commentCount字段，加1
        // 通过postId查询出帖子
        DiscussPost discussPost = discussPostMapper.selectByPrimaryKey(form.getPostId());
        discussPost.setCommentCount(discussPost.getCommentCount() + 1);
        // 保存修改
        int rows2 = discussPostMapper.updateByPrimaryKeySelective(discussPost);
        if (rows2 < 0) {
            map.put("msg", "修改帖子数量失败");
            return map;
        }
        return map;
    }
}
