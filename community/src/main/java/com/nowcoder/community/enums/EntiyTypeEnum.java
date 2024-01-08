package com.nowcoder.community.enums;

import lombok.Getter;

@Getter
public enum EntiyTypeEnum {
    POST(1),
    COMMENT(2)
    ;

    Integer code;

    EntiyTypeEnum(Integer code) {
        this.code = code;
    }
}
