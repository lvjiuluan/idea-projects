package com.nowcoder.community;

import com.nowcoder.community.util.MailClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


@SpringBootTest
@RunWith(SpringRunner.class)
public class CommunityApplicationTest {
    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private MailClient mailClient;

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
        
    }
}