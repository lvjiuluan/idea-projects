package com.nowcoder.community.enums;

import lombok.Getter;

@Getter
public enum DiscussPostStatusEnum {
    NORMAL(0),
    HIGHLIGHT(1),
    DELETED(2);
    Integer code;

    DiscussPostStatusEnum(Integer code) {
        this.code = code;
    }
}
