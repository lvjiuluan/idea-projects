package com.nowcoder.community.entity;

import java.util.Date;
import java.util.List;

import com.nowcoder.community.vo.CommentVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    private Integer id;

    private Integer userId;

    private Integer entityType;

    private Integer entityId;

    private Integer targetId;

    private Integer status;

    private Date createTime;

    private String content;

    // 子评论列表
    private List<CommentVo> commentVoList;

}