package com.nowcoder.community.vo;

import lombok.Data;

import java.util.Map;

@Data
public class MessageContentVo {
    private Integer entityType;
    private Integer entityId;
    private Integer userId;
    private Map<String, Object> data;
    private Integer postId;
}
