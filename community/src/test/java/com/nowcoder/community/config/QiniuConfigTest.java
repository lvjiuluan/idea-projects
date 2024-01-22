package com.nowcoder.community.config;

import com.nowcoder.community.CommunityApplicationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class QiniuConfigTest extends CommunityApplicationTest {
    @Autowired
    private QiniuConfig qiniuConfig;

    @Test
    public void test() {
        System.out.println(qiniuConfig.getKey().getAccess());
        System.out.println(qiniuConfig.getKey().getSecret());
        System.out.println(qiniuConfig.getBucket().get("share").getName());
        System.out.println(qiniuConfig.getBucket().get("share").getUrl());
    }
}