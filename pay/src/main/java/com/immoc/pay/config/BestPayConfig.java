package com.immoc.pay.config;

import com.lly835.bestpay.config.WxPayConfig;
import com.lly835.bestpay.service.BestPayService;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class BestPayConfig {
    @Bean
    public BestPayService bestPayService() {
        WxPayConfig wxPayConfig = new WxPayConfig();
        wxPayConfig.setAppId("wx3e6b9f1c5a7ff034"); // 公众号appId
        wxPayConfig.setMchId("1614433647"); // 商户号
        wxPayConfig.setMchKey("Aa111111111122222222223333333333"); // 商户密钥
        wxPayConfig.setNotifyUrl("http://mhdhgg.natappfree.cc/pay/notify");


        // 配置
        BestPayServiceImpl bestPayService = new BestPayServiceImpl();
        bestPayService.setWxPayConfig(wxPayConfig);
        return bestPayService;
    }
}
