package com.nowcoder.community.enums;

import lombok.Getter;

@Getter
public enum LoginTicketExpiredEnum {
    DEFALUT_EXPIRED_SECONDS(1000 * 3600 * 12L, "默认超时时间"),
    REMEMBERME_EXPIRED_SECONDS(1000 * 3600 * 24 * 100L, "记住我的超时时间");

    Long code;
    String desc;

    LoginTicketExpiredEnum(Long code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
