package com.nowcoder.community.util;

import com.nowcoder.community.CommunityApplicationTest;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class CommunityUtilTest extends CommunityApplicationTest {

    @Test
    public void getJSONString() {
        HashMap<String,Object> a = new HashMap<>();
        a.put("a", 1);
        a.put("b", 2);
        System.out.println(CommunityUtil.getJSONString(1, "SUCCESS", a));
    }
}