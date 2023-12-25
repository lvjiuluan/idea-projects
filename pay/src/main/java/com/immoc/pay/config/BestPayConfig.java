package com.immoc.pay.config;

import com.lly835.bestpay.config.WxPayConfig;
import com.lly835.bestpay.service.BestPayService;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class BestPayConfig {
    @Autowired
    WxAccountConfig wxAccountConfig;

    @Bean
    public BestPayService bestPayService(WxPayConfig wxPayConfig) {
        // 配置
        BestPayServiceImpl bestPayService = new BestPayServiceImpl();
        bestPayService.setWxPayConfig(wxPayConfig);
        return bestPayService;
    }

    @Bean
    public WxPayConfig wxPayConfig() {
        WxPayConfig wxPayConfig = new WxPayConfig();
        wxPayConfig.setAppId(wxAccountConfig.getAppId()); // 公众号appId
        wxPayConfig.setMchId(wxAccountConfig.getMchId()); // 商户号
        wxPayConfig.setMchKey(wxAccountConfig.getMchKey()); // 商户密钥
        wxPayConfig.setNotifyUrl(wxAccountConfig.getNotifyUrl());
        wxPayConfig.setReturnUrl(wxAccountConfig.getReturnUrl());
        return wxPayConfig;
    }
}
