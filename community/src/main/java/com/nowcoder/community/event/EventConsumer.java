package com.nowcoder.community.event;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.nowcoder.community.config.QiniuConfig;
import com.nowcoder.community.entity.DiscussPost;
import com.nowcoder.community.entity.Event;
import com.nowcoder.community.entity.Message;
import com.nowcoder.community.service.IDiscussPostService;
import com.nowcoder.community.service.IElasticsearchService;
import com.nowcoder.community.service.IMessageService;
import com.nowcoder.community.util.CommunityUtil;
import com.nowcoder.community.vo.MessageContentVo;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;

import static com.nowcoder.community.constant.EventTopicsConst.*;


@Component
@Slf4j
public class EventConsumer {
    @Autowired
    private IMessageService messageService;
    @Autowired
    private IDiscussPostService discussPostService;
    @Autowired
    private IElasticsearchService elasticsearchService;
    @Value("${wk.image.storage}")
    private String wkImageStorage;
    @Value("${wk.image.command}")
    private String wkImageCommand;
    @Autowired
    private QiniuConfig qiniuConfig;
    @Autowired
    private ThreadPoolTaskScheduler taskScheduler;

    @KafkaListener(topics = {COMMENT, LIKE, FOLLOW})
    public void handleCommentMessage(ConsumerRecord record) {
        if (record == null || record.value() == null) {
            log.error("消息的内容为空");
            return;
        }
        Event event = new Gson().fromJson(record.value().toString(), Event.class);
        if (event == null) {
            log.error("消息格式错误");
            return;
        }
        // 通过event构造Message数据
        Message message = new Message();
        message.setFromId(1);
        message.setToId(event.getEntityUserId());
        message.setConversationId(event.getTopic());
        // {"entityType":1,"entityId":237,"userId":112}
        MessageContentVo content = new MessageContentVo();
        content.setUserId(event.getUserId()); // 这个事件是又谁触发的
        content.setEntityType(event.getEntityType());
        content.setEntityId(event.getEntityId());
        if (!event.getData().isEmpty()) {
            if (event.getData("postId") != null) {
                content.setPostId(((Double) event.getData("postId")).intValue());
            } else {
                content.setData(event.getData());
            }
        }
        message.setContent(new Gson().toJson(content));


        messageService.addMessage(message);
    }

    @KafkaListener(topics = {PUBLISH})
    public void handlePublishMessage(ConsumerRecord record) {
        if (record == null || record.value() == null) {
            log.error("消息的内容为空");
            return;
        }
        Event event = new Gson().fromJson(record.value().toString(), Event.class);
        if (event == null) {
            log.error("消息格式错误");
            return;
        }
        // 处理事件
        // 1 查询出帖子
        DiscussPost discussPost = discussPostService.findDiscussPostById(event.getEntityId());
        // 2 存帖子
        elasticsearchService.saveDiscussPost(discussPost);
    }

    @KafkaListener(topics = {DELETE})
    public void handleDeletewhMessage(ConsumerRecord record) {
        if (record == null || record.value() == null) {
            log.error("消息的内容为空");
            return;
        }
        Event event = new Gson().fromJson(record.value().toString(), Event.class);
        if (event == null) {
            log.error("消息格式错误");
            return;
        }
        // 处理事件
        // 删除帖子
        elasticsearchService.deleteDiscussPost(event.getEntityId());
    }


    @KafkaListener(topics = {SHARE})
    public void handleShareMessage(ConsumerRecord record) {
        if (record == null || record.value() == null) {
            log.error("消息的内容为空");
            return;
        }
        Event event = new Gson().fromJson(record.value().toString(), Event.class);
        if (event == null) {
            log.error("消息格式错误");
            return;
        }
        // 处理事件
        // 保存图片
        String htmlUrl = (String) event.getData("htmlUrl");
        String fileName = (String) event.getData("fileName");
        String suffix = (String) event.getData("suffix");
        String cmd = wkImageCommand + " --quality 75 "
                + htmlUrl + " " + wkImageStorage + "/" + fileName + suffix;
        try {
            Runtime.getRuntime().exec(cmd);
            log.info("生成长图成功 {}", cmd);
        } catch (IOException e) {
            log.error("生成长图失败 {},{}", cmd, e);
        }
        // 人为启动一个定时器，每隔0.5s，查看图片是否生成
        // 如果没生成，就继续等
        // 如果等的时间超过30s，就认为失败
        // 如果发现图片已经生成了，就可以上传至七牛云
        UploadTask uploadTask = new UploadTask(fileName, suffix);
        Future future = taskScheduler.scheduleAtFixedRate(uploadTask, 500);
        uploadTask.setFuture(future);
    }

    class UploadTask implements Runnable {

        // 文件名称
        private String fileName;

        // 文件后缀
        private String suffix;

        // 开始时间
        private Long startTime;

        // 上传次数
        private Integer uploadTimes;

        // 启动任务的返回值，它可以用来停止定时器
        private Future future;

        public void setFuture(Future future) {
            this.future = future;
        }

        public UploadTask(String fileName, String suffix) {
            this.fileName = fileName;
            this.suffix = suffix;
            this.startTime = System.currentTimeMillis();
            this.uploadTimes = 0;
        }

        @Override
        public void run() {
            // 传不上去的情况
            // 1 30s之后还没找到文件,生成图片失败
            if (System.currentTimeMillis() - startTime > 30000) {
                log.error("执行时间过长，终止任务" + fileName);
                future.cancel(true);
                return;
            }
            // 2 七牛云服务器出问题 上传失败
            if (uploadTimes >= 3) {
                log.error("上传次数过多，终止任务" + fileName);
                future.cancel(true);
                return;
            }
            // 执行传文件逻辑
            // 1 本地路径查找文件
            String path = wkImageStorage + "/" + fileName + suffix;
            File file = new File(path);
            if (file.exists()) {
                // 开始传
                log.info(String.format("开始第%d次上传[%s]", ++uploadTimes, fileName));
                // 设置相应信息
                StringMap policy = new StringMap();
                policy.put("returnBody", CommunityUtil.getJSONString(0));
                // 生成上传凭证
                Auth auth = Auth.create(qiniuConfig.getKey().getAccess(),
                        qiniuConfig.getKey().getSecret());
                String uploadToken = auth.uploadToken(
                        qiniuConfig.getBucket().get("share").getName(),
                        fileName,
                        3600,
                        policy
                );
                // 通过java地址指定上传的机房
                UploadManager manager = new UploadManager(new Configuration(Zone.zone2()));
                try {
                    Response response = manager.put(
                            path,
                            fileName,
                            uploadToken,
                            null,
                            "image/png",
                            false
                    );
                    // 处理相应结果
                    JSONObject json = JSONObject.parseObject(response.bodyString());
                    if (json == null ||
                            json.get("code") == null ||
                            StringUtils.isBlank(json.get("code").toString()) ||
                            !json.get("code").toString().equals("0")) {
                        // 上传文件失败
                        log.info(String.format("第%d次上传失败[%s]", uploadTimes, fileName));
                    } else {
                        // 上传文件成功
                        log.info(String.format("第%d上传成功[%s]", uploadTimes, fileName));
                        future.cancel(true);
                        log.info("上传文件任务结束");
                    }
                } catch (QiniuException e) {
                    log.info(String.format("第%d次上传失败[%s]", uploadTimes, fileName));
                }
            } else {
                log.info(String.format("等待图片生成...[%s]", fileName));
            }
        }
    }
}
