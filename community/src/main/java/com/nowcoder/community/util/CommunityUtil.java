package com.nowcoder.community.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;

public class CommunityUtil {
    // 生成随机字符串
    public static String generateUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    // MD5 对密码加密
    // 只能加密，不能解密
    // 加盐处理，加上随机的字符串
    public static String md5(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        return DigestUtils.md5DigestAsHex(key.getBytes(StandardCharsets.UTF_8));
    }

    /*
     *  服务器想浏览器返回JSON数据的内容整合
     * */
    public static String getJSONString(Integer code, String msg, Map<String, Object> map) {
        // 将它们封装到json对象
        JSONObject json = new JSONObject();
        json.put("code", code);
        if (msg != null) {
            json.put("msg", msg);
        }
        if (map != null) {
            // 遍历map
            for (String key : map.keySet()) {
                json.put(key, map.get(key));
            }
        }
        return json.toJSONString();
    }

    /*
     *  服务器想浏览器返回JSON数据的内容整合
     * */
    public static String getJSONString(Integer code, String msg) {
        return getJSONString(code, msg, null);
    }

    public static String getJSONString(Integer code) {
        return getJSONString(code, null, null);
    }

    public static String getJSONString(Integer code, Map<String, Object> map) {
        return getJSONString(code, null, map);
    }
}
