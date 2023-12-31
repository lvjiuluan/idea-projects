package com.immoc.mall.listener;

import com.google.gson.Gson;
import com.immoc.mall.pojo.PayInfo;
import com.immoc.mall.service.impl.OrderServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "payNotify")
@Slf4j
public class PayMsqListener {
    @Autowired
    private OrderServiceImpl orderService;

    @RabbitHandler
    public void process(String msg) {
        log.info("接收到消息: {}", msg);
        Gson gson = new Gson();
        /*
         * 关于payinfo正确姿势，因该是pay项目提供一个client.jar，然后mall项目引用这个jar包
         * */
        PayInfo payInfo = gson.fromJson(msg, PayInfo.class);
        if (payInfo.getPlatformStatus().equals("SUCCESS")) {
            // 修改订单里的状态
            Long orderNo = payInfo.getOrderNo();
            orderService.paid(orderNo);
        }
    }
}
