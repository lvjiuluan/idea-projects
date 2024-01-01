package com.nowcoder.community.pojo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    private Integer id;

    private String userId;

    private String title;

    private Integer type;

    private Integer status;

    private Date createTime;

    private Integer commentCount;

    private Double score;

    private String content;
}