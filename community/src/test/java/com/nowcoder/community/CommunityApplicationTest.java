package com.nowcoder.community;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nowcoder.community.entity.LoginTicket;
import com.nowcoder.community.form.LoginForm;
import com.nowcoder.community.util.CommunityUtil;
import com.nowcoder.community.util.MailClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Date;


@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class CommunityApplicationTest {

    public Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private MailClient mailClient;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private KafkaProducer kafkaProducer;



    @Test
    public void kafkaTest() {
        kafkaProducer.sendMsg("test","你好");
        kafkaProducer.sendMsg("test","在吗");
        try {
            Thread.sleep(1000  * 10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testHtmlMail() {
        Context context = new Context();
        context.setVariable("username", "吕九峦");
        String content = templateEngine.process("/mail/demo", context);
        System.out.println(content);

//        mailClient.sendMail("1342409485@qq.com", "HTML", content);
    }

    @Test
    public void testRedis() {
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setId(1);
        loginTicket.setStatus(0);
        loginTicket.setExpired(new Date());
        loginTicket.setTicket("test");
        ValueOperations opsForValue = stringRedisTemplate.opsForValue();
        opsForValue.set(loginTicket.getTicket(), gson.toJson(loginTicket));

    }

    @Test
    public void testGetFromRedis() {
        ValueOperations<String, String> opsForValue = stringRedisTemplate.opsForValue();
        String loginTicketString = opsForValue.get("a08dd45431c5499ebcd2817bf3e40e8f");
        LoginTicket loginTicket = gson.fromJson(loginTicketString, LoginTicket.class);
        log.info("loginTicket = {}", loginTicket);
        Date expired = loginTicket.getExpired();
        System.out.println(expired.after(new Date()));
    }

    @Test
    public void GenerateMd5() {
        System.out.println(CommunityUtil.md5("147258364f5"));
    }
}

@Component
class KafkaProducer {
    @Autowired
    private KafkaTemplate kafkaTemplate;

    public void sendMsg(String topic, String content) {
        kafkaTemplate.send(topic, content);
    }
}

@Component
class KafkaConsumer {
    @KafkaListener(topics = {"test"})
    public void handleMessage(ConsumerRecord record) {
        System.out.println(record.value());
    }
}