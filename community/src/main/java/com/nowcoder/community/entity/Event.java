package com.nowcoder.community.entity;

import java.util.HashMap;
import java.util.Map;


public class Event {
    private String topic;
    private Integer userId; // 事件触发的人
    private Integer entityType;
    private Integer entityId;
    private Integer entityUserId; // 实体的作者
    private Map<String, Object> data = new HashMap<>();

    // 方便链式调用
    public Event setTopic(String topic) {
        this.topic = topic;
        return this;
    }

    public Event setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public Event setEntityType(Integer entityType) {
        this.entityType = entityType;
        return this;
    }

    public Event setEntityId(Integer entityId) {
        this.entityId = entityId;
        return this;
    }

    public Event setEntityUserId(Integer entityUserId) {
        this.entityUserId = entityUserId;
        return this;
    }

    public Event setData(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    public String getTopic() {
        return topic;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getEntityType() {
        return entityType;
    }

    public Integer getEntityId() {
        return entityId;
    }

    public Integer getEntityUserId() {
        return entityUserId;
    }

    public Object getData(String key) {
        return data.get(key);
    }


    public Map<String, Object> getData() {
        return data;
    }
}
