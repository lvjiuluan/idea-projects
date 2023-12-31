package com.immoc.pay.service.impl;

import com.google.gson.Gson;
import com.immoc.pay.dao.PayInfoMapper;
import com.immoc.pay.pojo.PayInfo;
import com.immoc.pay.service.IPayService;
import com.lly835.bestpay.config.WxPayConfig;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.enums.OrderStatusEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.service.BestPayService;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;

@Slf4j
@Service
public class PayServiceImpl implements IPayService {
    private static final String QUEUE_PAY_NOTIFY = "payNotify";
    @Autowired
    private BestPayService bestPayService;
    @Autowired
    private PayInfoMapper payInfoMapper;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Override
    public PayResponse create(String orderId, BigDecimal amount) {
        // 写入数据库 订单号-金额
        PayInfo payInfo = new PayInfo(Long.parseLong(orderId), 2, OrderStatusEnum.NOTPAY.name(), amount);
        payInfoMapper.insertSelective(payInfo); // 有选择的添加
        // 发起支付
        PayRequest payRequest = new PayRequest();
        payRequest.setOrderName("测试");
        payRequest.setOrderId(orderId);
        payRequest.setOrderAmount(amount.doubleValue());
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_NATIVE);


        PayResponse payResponse = bestPayService.pay(payRequest);
        log.info("发起支付的 response = {}", payResponse);
        return payResponse;
    }


    @Override
    public String asyncNotify(String notifyData) {
        // 1 签名校验
        PayResponse payResponse = bestPayService.asyncNotify(notifyData);
        log.info("微信后台发送的 payResponse={}", payResponse);
        // 2 校验金额(根据订单号查询)
        PayInfo payInfo = payInfoMapper.selectByOrderNo(Long.parseLong(payResponse.getOrderId()));
        // 这种情况比较严重，发出警告：钉钉、短信
        if (payInfo == null) {
            throw new RuntimeException("通过orderNo查询到的结果是null");
        }
        // 如果订单状态不是已支付
        if (!payInfo.getPlatformStatus().equals(OrderStatusEnum.SUCCESS.name())) {
            // 则比较金额对不对
            if (payInfo.getPayAmount().compareTo(BigDecimal.valueOf(payResponse.getOrderAmount())) != 0) {
                //
                throw new RuntimeException("异步通知中的金额和数据库里不一致, orderNo = " + payResponse.getOrderId());
            }
            // 3 修改订单为支付状态
            payInfo.setPlatformStatus(OrderStatusEnum.SUCCESS.name());
            // 交易流水号
            payInfo.setPlatformNumber(payResponse.getOutTradeNo());
            // 修改时间
            log.info("payInfo={}", payInfo);
            payInfoMapper.updateByPrimaryKeySelective(payInfo);
        }
        //TODO pay发送MQ消息， mall接收MQ消息
        Gson gson = new Gson();
        amqpTemplate.convertAndSend(QUEUE_PAY_NOTIFY, gson.toJson(payInfo));
        // 4 告诉微信不要再通知了
        return "<xml>\n" +
                "   <return_code><![CDATA[SUCCESS]]></return_code>\n" +
                "   <return_msg><![CDATA[OK]]></return_msg>\n" +
                "</xml>";
    }

    // 查询支付结果
    public PayInfo queryByOrderId(String orderId) {
        return payInfoMapper.selectByOrderNo(Long.parseLong(orderId));
    }
}
