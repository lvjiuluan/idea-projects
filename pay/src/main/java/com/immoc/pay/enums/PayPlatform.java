package com.immoc.pay.enums;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import lombok.Getter;

@Getter
public enum PayPlatform {
    // 1 支付宝 2微信
    ALPAY(1),
    WX(2);
    Integer code;

    PayPlatform(Integer code) {
        this.code = code;
    }

    public static PayPlatform getByBestPayType(BestPayTypeEnum bestPayTypeEnum) {
        for (PayPlatform value : PayPlatform.values()) {
            if (bestPayTypeEnum.getPlatform().getName().equals(value.name())) {
                return value;
            }
        }
        throw new RuntimeException("错误的支付平台" + bestPayTypeEnum.name());
    }
}
