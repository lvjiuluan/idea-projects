package com.nowcoder.community.config;

import com.nowcoder.community.CommunityApplicationTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

@Slf4j
public class MailConfigTest extends CommunityApplicationTest {
    @Autowired
    private MailConfig mailConfig;

    @Test
    public void test() {
        log.info("mailConfig = {}", mailConfig);
    }

}