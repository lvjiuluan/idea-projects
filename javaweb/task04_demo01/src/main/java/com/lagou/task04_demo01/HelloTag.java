package com.lagou.task04_demo01;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class HelloTag extends SimpleTagSupport{
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public void doTag() throws JspException, IOException {
        // 获取输出流
        JspWriter out = this.getJspContext().getOut();
        out.println("自定义标签哦" + this.name);
        out.close();
    }
}
