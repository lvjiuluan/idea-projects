package com.nowcoder.community;

import com.google.gson.Gson;
import com.nowcoder.community.entity.LoginTicket;
import com.nowcoder.community.form.LoginForm;
import com.nowcoder.community.util.MailClient;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Date;


@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class CommunityApplicationTest {
    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private MailClient mailClient;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    Gson gson = new Gson();

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
        String loginTicketString = opsForValue.get("test");
        LoginTicket loginTicket = gson.fromJson(loginTicketString, LoginTicket.class);
        log.info("loginTicket = {}", loginTicket);
    }
}