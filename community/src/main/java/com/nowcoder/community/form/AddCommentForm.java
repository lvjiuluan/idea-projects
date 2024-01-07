package com.nowcoder.community.form;

import lombok.Data;

@Data
public class AddCommentForm {

    private Integer postId;

    private Integer entityType;

    private Integer entityId;

    private Integer targetId;

    private String content;
}
