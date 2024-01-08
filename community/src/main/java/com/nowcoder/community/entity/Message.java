package com.nowcoder.community.entity;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private Integer id;

    private Integer fromId;

    private Integer toId;

    private String conversationId;

    private Integer status;

    private Date createTime;

    private String content;
}