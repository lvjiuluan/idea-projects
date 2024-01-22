package com.nowcoder.community.controller;

import com.nowcoder.community.constant.EventTopicsConst;
import com.nowcoder.community.entity.Event;
import com.nowcoder.community.event.EventProducer;
import com.nowcoder.community.util.CommunityUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
public class ShareController {
    // 事件驱动 将事件丢给kafaka
    @Autowired
    private EventProducer eventProducer;
    @Value("${community.path.domain}")
    private String domain;
    @Value("${server.servlet.context-path}")
    private String contextPath;
    @Value("${wk.image.storage}")
    private String wkImageStorage;

    @GetMapping("/share")
    @ResponseBody
    public String share(String htmlUrl) {
        // 随机图片文件名
        String fileName = CommunityUtil.generateUUID();
        // 构建Event
        Event event = new Event()
                .setTopic(EventTopicsConst.SHARE)
                .setData("htmlUrl", htmlUrl)
                .setData("fileName", fileName)
                .setData("suffix", ".png");
        // 异步生成长图
        eventProducer.fireEvent(event);
        // 返回访问路径
        Map<String, Object> map = new HashMap<>();
        map.put("shareUrl", domain + contextPath + "/share/image/" + fileName);
        return CommunityUtil.getJSONString(0, null, map);
    }

    // 获取长图
    @GetMapping("/share/image/{fileName}")
    public void getShareImage(@PathVariable String fileName,
                              HttpServletResponse response) {
        // 判断参数
        if (StringUtils.isBlank(fileName)) {
            throw new IllegalArgumentException("文件名不能为空");
        }
        response.setContentType("image/png");
        File file = new File(wkImageStorage + "/" + fileName + ".png");
        try {
            ServletOutputStream out = response.getOutputStream();
            FileInputStream in = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int b = 0;
            while ((b = in.read(buffer)) != -1) {
                out.write(buffer, 0, b);
            }
        } catch (IOException e) {
            log.error("获取长图失败: " + e.getMessage());
        }

    }
}
