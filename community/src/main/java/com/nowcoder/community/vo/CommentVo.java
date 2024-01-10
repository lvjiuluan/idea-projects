package com.nowcoder.community.vo;

import com.nowcoder.community.entity.Comment;
import com.nowcoder.community.entity.User;
import lombok.Data;

import java.util.Date;

@Data
public class CommentVo {
    private Integer id;

    private Integer userId;

    private Integer entityType;

    private Integer entityId;

    private Integer targetId;

    private Integer status;

    private Date createTime;

    private String content;

    private User user;

    private User targetUser;

    private Long likeCount;

    private Integer likeStatus;

}
