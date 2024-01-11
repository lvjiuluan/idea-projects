package com.nowcoder.community.vo;

import lombok.Data;

import java.util.Date;

@Data
public class UserFollowVo {
    private Integer id;

    private String username;

    private String password;

    private String salt;

    private String email;

    private Integer type;

    private Integer status;

    private String activationCode;

    private String headerUrl;

    private Date createTime;

    private Date followTime;

    private Boolean hasFollowed;
}
