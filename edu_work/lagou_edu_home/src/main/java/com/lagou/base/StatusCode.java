package com.lagou.base;

import com.alibaba.fastjson.JSONObject;

public enum StatusCode {
    // 枚举响应的状态码
    SUCCESS(0,"sucess"),FAIL(1,"fail");
    /***
     * SUCCESS(0,"sucess") 在编译过程中等价于：
     *  public static StatusCode SUCCESS = new StatusCode(0,"sucess");
     *  私有化构造函数，保证只有这两个对象
     */
    // 定义属性
    private int code;
    private String message;

    StatusCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public String toString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status",code);
        jsonObject.put("msg",message);
        return jsonObject.toString();
    }
}
