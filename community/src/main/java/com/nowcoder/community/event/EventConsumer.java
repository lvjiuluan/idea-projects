package com.nowcoder.community.event;

import com.google.gson.Gson;
import com.nowcoder.community.entity.DiscussPost;
import com.nowcoder.community.entity.Event;
import com.nowcoder.community.entity.Message;
import com.nowcoder.community.service.IDiscussPostService;
import com.nowcoder.community.service.IElasticsearchService;
import com.nowcoder.community.service.IMessageService;
import com.nowcoder.community.vo.MessageContentVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

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
}
