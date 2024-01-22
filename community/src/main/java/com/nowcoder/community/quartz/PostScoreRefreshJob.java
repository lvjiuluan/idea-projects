package com.nowcoder.community.quartz;

import com.nowcoder.community.entity.DiscussPost;
import com.nowcoder.community.enums.DiscussPostStatusEnum;
import com.nowcoder.community.enums.EntiyTypeEnum;
import com.nowcoder.community.service.IDiscussPostService;
import com.nowcoder.community.service.IElasticsearchService;
import com.nowcoder.community.service.ILikeService;
import com.nowcoder.community.util.RedisKeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

// 帖子分数刷新 定时任务
@Slf4j
public class PostScoreRefreshJob implements Job {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private IDiscussPostService discussPostService;

    @Autowired
    private ILikeService likeService;

    @Autowired
    private IElasticsearchService elasticsearchService;

    private static final Date epoch;

    static {
        try {
            epoch = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2014-08-01 00:00:00");
        } catch (ParseException e) {
            throw new RuntimeException("初始化牛客网纪元失败!", e);
        }
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String redisKey = RedisKeyUtil.getPostScoreKey();
        BoundSetOperations<String, Object> operations = redisTemplate.boundSetOps(redisKey);

        // 判断这个redisKey里有没有数据
        if (operations == null || operations.size() == 0) {
            log.info("[任务取消]，没有需要刷新的帖子");
            return;
        }
        long size = operations.size();
        log.info("[任务开始]，正在刷新帖子分数,一共需要刷新" + operations.size() + "条帖子");
        while (operations.size() > 0) {
            this.refresh((Integer) operations.pop());
        }
        log.info("[任务结束]，刷新了帖子+" + size + "+的分数");

    }

    private void refresh(Integer postId) {
        // 查询帖子
        DiscussPost post = discussPostService.findDiscussPostById(postId);
        // 判断是否存在
        if (post == null) {
            log.error("该帖子不存在, Id = " + postId);
            return;
        }
        // 是否加精
        Boolean highlight = post.getStatus() == DiscussPostStatusEnum.HIGHLIGHT.getCode();
        // 评论数量
        Integer commentCount = post.getCommentCount();
        // 点赞数量
        Long likeCount = likeService.findEntityLikeCount(EntiyTypeEnum.POST.getCode(), postId);

        // 计算分数, 权重部分
        double w = (highlight ? 75 : 0) + commentCount * 10 + likeCount * 2;
        // 分数 = 帖子权重 + 距离天数
        double score = Math.log10(Math.max(w, 1.0)) + (post.getCreateTime().getTime() - epoch.getTime()) / (3600 * 1000 * 24);
        // 更新帖子分数
        post.setScore(score);
        discussPostService.updateDiscussPost(post);
        // 同步搜索的数据
        elasticsearchService.saveDiscussPost(post);
    }
}
