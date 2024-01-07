package com.nowcoder.community.controller;

import com.nowcoder.community.util.CommunityUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DemoController {

    // ajax示例
    @PostMapping("/ajax")
    @ResponseBody
    public String testAjax(String name, Integer age) {

        return CommunityUtil.getJSONString(0, "异步请求成功！");
    }
}
