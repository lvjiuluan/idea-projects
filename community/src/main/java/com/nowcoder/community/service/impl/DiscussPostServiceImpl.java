package com.nowcoder.community.service.impl;

import com.nowcoder.community.dao.CommentMapper;
import com.nowcoder.community.dao.DiscussPostMapper;
import com.nowcoder.community.entity.Comment;
import com.nowcoder.community.entity.DiscussPost;
import com.nowcoder.community.entity.Page;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.service.ICommentService;
import com.nowcoder.community.service.IDiscussPostService;
import com.nowcoder.community.service.IUserService;
import com.nowcoder.community.util.HostHolder;
import com.nowcoder.community.util.SensitiveFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DiscussPostServiceImpl implements IDiscussPostService {
    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Autowired
    private SensitiveFilter sensitiveFilter;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private IUserService userService;

    @Autowired
    private SimpleDateFormat simpleDateFormat;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private ICommentService commentService;

    @Override
    public List<DiscussPost> findDiscussPosts(Integer userId, Integer offset, Integer limit) {
        return discussPostMapper.selectDiscussPosts(userId, offset, limit);
    }

    @Override
    public Integer findDiscussPostRows(Integer userId) {
        return discussPostMapper.selectDiscussPostRows(userId);
    }

    @Override
    public Map<String, Object> addDiscussPost(DiscussPost discussPost) {
        Map<String, Object> map = new HashMap<>();
        if (discussPost == null) {
            map.put("msg", "参数不能为空");
            return map;
        }
        // 设置默认值
        // 过滤敏感词
        // 把html标签处理掉
        discussPost.setTitle(HtmlUtils.htmlEscape(discussPost.getTitle()));
        discussPost.setContent(HtmlUtils.htmlEscape(discussPost.getContent()));
        discussPost.setTitle(sensitiveFilter.replaceSensitiveWords(discussPost.getTitle()));
        discussPost.setContent(sensitiveFilter.replaceSensitiveWords(discussPost.getContent()));
        User user = hostHolder.getUser();
        if (user == null) {
            map.put("msg", "请先登录");
            return map;
        }
        discussPost.setUserId(user.getId());
        int rows = discussPostMapper.insertSelective(discussPost);
        if (rows <= 0) {
            map.put("msg", "新增帖子失败");
            return map;
        }
        return map;
    }

    /*
     * TODO 1 用枚举类型来维护实体类型 entityType
     *      2 每页默认显示5条评论
     *      3 要把整个user返回，因为后续需要点赞，...信息尽量冗余，方便后续开发
     *      4 返回每个评论回复的数量
     * */
    @Override
    public Map<String, Object> findPostAndUserById(Integer id, Page page) {
        Map<String, Object> map = new HashMap<>();
        if (id == null) {
            throw new IllegalArgumentException("参数错误");
        }
        DiscussPost discussPost = discussPostMapper.selectByPrimaryKey(id);
        if (discussPost == null) {
            throw new RuntimeException("查询帖子失败！");
        }
        User user = userService.findUserById(discussPost.getUserId());
        if (user == null) {
            throw new RuntimeException("查询用户失败！");
        }
        map.put("post", discussPost);
        map.put("user", user);
        // 还需把loginUser查出来,以此判断其权限
        User loginUser = hostHolder.getUser();
        map.put("loginUser", loginUser);

        // 分页查询评论数据
        // 整理分页条件
        Integer rows = commentMapper.selectTotalRowsByEntity(1, id);
        page.setRows(rows);
        map.put("page", page);
        List<Comment> commentList = commentMapper.selectCommentListByEntity(1, id, page.getOffset(), page.getPageSize());
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (Comment comment : commentList) {
            comment.setCommentVoList(commentService.findCommentsById(comment.getId()));
            Map<String, Object> tempMap = new HashMap<>();
            user = userService.findUserById(comment.getUserId());
            tempMap.put("comment", comment);
            tempMap.put("user", user);
            mapList.add(tempMap);
        }
        map.put("mapList", mapList);
        return map;
    }
}
