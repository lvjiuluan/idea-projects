package com.nowcoder.community.enums;

import lombok.Getter;

@Getter
public enum EntiyTypeEnum {
    POST(1, "帖子"),
    COMMENT(2, "回复"),
    USER(3, "用户");

    Integer code;
    String desc;

    EntiyTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String getType(Integer code) {
        if (POST.getCode().equals(code)) return POST.desc;
        if (COMMENT.getCode().equals(code)) return COMMENT.desc;
        return null;
    }
}
