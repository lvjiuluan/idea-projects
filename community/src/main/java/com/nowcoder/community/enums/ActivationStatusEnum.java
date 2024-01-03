package com.nowcoder.community.enums;

import lombok.Getter;

@Getter
public enum ActivationStatusEnum {
    SUCCESS(0, "激活成功，您的账号可以使用"),
    REPEAT(1, "重复激活，无效的操作，该账号已经激活"),
    FAILURE(2, "激活失败，您提供的激活码有误");
    Integer code;
    String desc;

    ActivationStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
