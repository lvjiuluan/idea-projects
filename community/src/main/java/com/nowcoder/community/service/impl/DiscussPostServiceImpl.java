package com.nowcoder.community.service.impl;

import com.nowcoder.community.dao.CommentMapper;
import com.nowcoder.community.dao.DiscussPostMapper;
import com.nowcoder.community.entity.*;
import com.nowcoder.community.enums.DiscussPostStatusEnum;
import com.nowcoder.community.enums.EntiyTypeEnum;
import com.nowcoder.community.event.EventProducer;
import com.nowcoder.community.service.ICommentService;
import com.nowcoder.community.service.IDiscussPostService;
import com.nowcoder.community.service.ILikeService;
import com.nowcoder.community.service.IUserService;
import com.nowcoder.community.util.HostHolder;
import com.nowcoder.community.util.RedisKeyUtil;
import com.nowcoder.community.util.SensitiveFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.nowcoder.community.constant.EventTopicsConst.DELETE;
import static com.nowcoder.community.constant.EventTopicsConst.PUBLISH;

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

    @Autowired
    private ILikeService likeService;

    @Autowired
    private EventProducer eventProducer;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public List<DiscussPost> findDiscussPosts(Integer userId, Integer offset, Integer limit, Integer orderMode) {
        return discussPostMapper.selectDiscussPosts(userId, offset, limit, orderMode);
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
        /*
         * 发送成功后向Elasticsearch存一份帖子数据
         * 使用kafka消息队列存一个事件，让后台慢慢处理
         * */
        Event event = new Event()
                .setTopic(PUBLISH)
                .setUserId(user.getId())
                .setEntityType(EntiyTypeEnum.POST.getCode())
                .setEntityId(discussPost.getId());
        eventProducer.fireEvent(event);
        // 添加帖子会把post存到cache中，定时计算分数
        String redisKey = RedisKeyUtil.getPostScoreKey();
        redisTemplate.opsForSet().add(redisKey, discussPost.getId());
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
        // 加上该帖子的点赞数量
        Long likeCount = likeService.findEntityLikeCount(1, id);
        map.put("likeCount", likeCount);
        // 加上该当前登录用户是否对该帖子已赞、
        Integer likeStatus = likeService.findEntityLikeStatus(hostHolder.getUser().getId(), 1, id);
        map.put("likeStatus", likeStatus);
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
            // 加上该回帖的点赞数量
            likeCount = likeService.findEntityLikeCount(2, comment.getId());
            tempMap.put("likeCount", likeCount);
            // 加上该当前用户是否对该回帖已赞
            likeStatus = likeService.findEntityLikeStatus(hostHolder.getUser().getId(), 2, comment.getId());
            tempMap.put("likeStatus", likeStatus);
            mapList.add(tempMap);
        }
        map.put("mapList", mapList);
        return map;
    }

    @Override
    public DiscussPost findDiscussPostById(Integer postId) {
        return discussPostMapper.selectByPrimaryKey(postId);
    }

    @Override
    public Map<String, Object> top(Integer postId) {

        // 根据postId查询帖子
        DiscussPost discussPost = findDiscussPostById(postId);
        if (discussPost == null) {
            throw new RuntimeException("服务器错误");
        }
        // 如果本身的type已经置顶，那么就是取消置顶
        if (discussPost.getType().equals(1)) {
            discussPost.setType(0);
        } else {
            discussPost.setType(1);
        }
        int rows = discussPostMapper.updateByPrimaryKeySelective(discussPost);
        if (rows <= 0) {
            throw new RuntimeException("服务器错误");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("post", discussPost);
        Event event = new Event()
                .setTopic(PUBLISH)
                .setUserId(hostHolder.getUser().getId())
                .setEntityType(EntiyTypeEnum.POST.getCode())
                .setEntityId(discussPost.getId());
        eventProducer.fireEvent(event);
        return map;
    }

    @Override
    public Map<String, Object> delete(Integer postId) {
        // 根据postId查询帖子
        DiscussPost discussPost = findDiscussPostById(postId);
        if (discussPost == null) {
            throw new RuntimeException("服务器错误");
        }
        discussPost.setStatus(DiscussPostStatusEnum.DELETED.getCode());
        int rows = discussPostMapper.updateByPrimaryKeySelective(discussPost);
        if (rows <= 0) {
            throw new RuntimeException("服务器错误");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("post", discussPost);
        Event event = new Event()
                .setTopic(DELETE)
                .setUserId(hostHolder.getUser().getId())
                .setEntityType(EntiyTypeEnum.POST.getCode())
                .setEntityId(discussPost.getId());
        eventProducer.fireEvent(event);
        return map;
    }

    @Override
    public Map<String, Object> highlight(Integer postId) {
        // 根据postId查询帖子
        DiscussPost discussPost = findDiscussPostById(postId);
        if (discussPost == null) {
            throw new RuntimeException("服务器错误");
        }
        // 如果本身的type已经加精，那么就是取消加精
        if (discussPost.getStatus().equals(DiscussPostStatusEnum.HIGHLIGHT.getCode())) {
            discussPost.setStatus(DiscussPostStatusEnum.NORMAL.getCode());
        } else {
            discussPost.setStatus(DiscussPostStatusEnum.HIGHLIGHT.getCode());
        }
        int rows = discussPostMapper.updateByPrimaryKeySelective(discussPost);
        if (rows <= 0) {
            throw new RuntimeException("服务器错误");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("post", discussPost);
        Event event = new Event()
                .setTopic(PUBLISH)
                .setUserId(hostHolder.getUser().getId())
                .setEntityType(EntiyTypeEnum.POST.getCode())
                .setEntityId(discussPost.getId());
        eventProducer.fireEvent(event);

        // 加精会把post存到cache中，定时计算分数
        String redisKey = RedisKeyUtil.getPostScoreKey();
        redisTemplate.opsForSet().add(redisKey, discussPost.getId());

        return map;
    }

    @Override
    public Integer updateDiscussPost(DiscussPost discussPost) {
        return discussPostMapper.updateByPrimaryKeySelective(discussPost);
    }
}
