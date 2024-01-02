package com.nowcoder.community.util;

import com.nowcoder.community.CommunityApplicationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class MailClientTest extends CommunityApplicationTest {

    @Autowired
    private MailClient mailClient;

    @Test
    public void sendMail() {
        mailClient.sendMail("1342409485@qq.com", "TEST", "Welcome.");
    }
}