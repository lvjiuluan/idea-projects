package com.nowcoder.community.service.impl;

import com.nowcoder.community.dao.CommentMapper;
import com.nowcoder.community.dao.DiscussPostMapper;
import com.nowcoder.community.dao.UserMapper;
import com.nowcoder.community.entity.Comment;
import com.nowcoder.community.entity.DiscussPost;
import com.nowcoder.community.entity.Event;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.enums.EntiyTypeEnum;
import com.nowcoder.community.event.EventProducer;
import com.nowcoder.community.form.AddCommentForm;
import com.nowcoder.community.service.ICommentService;
import com.nowcoder.community.service.ILikeService;
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

import static com.nowcoder.community.constant.EventTopicsConst.COMMENT;
import static com.nowcoder.community.constant.EventTopicsConst.PUBLISH;

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
    @Autowired
    private ILikeService likeService;

    @Autowired
    private EventProducer eventProducer;

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
            // 加上该回复的点赞数量
            Long likeCount = likeService.findEntityLikeCount(2, commentVo.getId());
            commentVo.setLikeCount(likeCount);
            // 加上该当前登录用户是否对该回帖已赞
            Integer likeStatus = likeService.findEntityLikeStatus(hostHolder.getUser().getId(), 2, comment.getId());
            commentVo.setLikeStatus(likeStatus);
            if (!comment.getTargetId().equals(0)) {
                User targetUser = userMapper.selectByPrimaryKey(comment.getTargetId());
                commentVo.setTargetUser(targetUser);
            }
            commentVoList.add(commentVo);
        }
        return commentVoList;

    }

    /*
     *
     * TODO 1 事务传播机制
     *      2 不通过加1跟新commentCount，通过查询该帖子id下的评论数量，
     *        然后更新commentCount，且回评论只包括回复帖子的评论，不包括回复评论的评论
     *      3 在contrller通过重定向重新渲染该页面，
     *      4 前端通过<input type="hidden">来传变量给后端
     *      5 手动在前端补充上表单,且type="button"要改成type="submit"
     *
     *
     * */

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
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
        // 通过commentMapper查询出回复postId的评论
        Integer commentCount = commentMapper.selectTotalRowsByEntity(EntiyTypeEnum.POST.getCode(), form.getPostId());
        discussPost.setCommentCount(commentCount);
        // 保存修改
        int rows2 = discussPostMapper.updateByPrimaryKeySelective(discussPost);
        if (rows2 < 0) {
            map.put("msg", "修改帖子回帖数量失败");
            return map;
        }
        /*
         * 触发评论事件
         * */
        // 1 构造事件对象
        Event event = new Event()
                .setTopic(COMMENT)
                .setUserId(hostHolder.getUser().getId())
                .setEntityType(comment.getEntityType())
                .setEntityId(comment.getEntityId())
                .setData("postId", form.getPostId());
        if (EntiyTypeEnum.POST.getCode().equals(comment.getEntityType())) {
            DiscussPost target = discussPostMapper.selectByPrimaryKey(comment.getEntityId());
            event.setEntityUserId(target.getUserId());
        } else if (EntiyTypeEnum.COMMENT.getCode().equals(comment.getEntityType())) {
            Comment target = commentMapper.selectByPrimaryKey(comment.getEntityId());
            event.setEntityUserId(target.getUserId());
        }
        eventProducer.fireEvent(event);
        /*
         * 如果评论的是帖子，则帖子的回帖数量commentCount会增加
         * 此时要修改commentCount并覆盖elasticsearch原来存的一份
         * */
        if (EntiyTypeEnum.POST.getCode().equals(comment.getEntityType())) {
            /*
             * 触发发帖事件
             * */
            event = new Event()
                    .setTopic(PUBLISH)
                    .setUserId(comment.getUserId())
                    .setEntityType(EntiyTypeEnum.POST.getCode())
                    .setEntityId(comment.getEntityId());
            eventProducer.fireEvent(event);
        }
        return map;
    }
}
