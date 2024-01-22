package com.nowcoder.community.quartz;

import java.io.IOException;

public class Wktohtml {
    public static void main(String[] args) {
        String cmd = "D:/wkhtmltopdf/bin/wkhtmltoimage --quality 75 https://www.nowcoder.com/ d:/wkhtmltopdf/data/wk-image/3.png";
        try {
            Runtime.getRuntime().exec(cmd);
            System.out.println("ok");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
