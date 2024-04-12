package utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class StringUtils {
    public static void main(String[] args) {
        function();
    }

    public static String toUpperCase(String str) {
        function();
        return str;
    }

    public static void function() {
        try {
            Process p = Runtime.getRuntime().exec("python \"D:\\IDEA Projects\\一些项目级别的学习\\蓝桥杯2024\\代码\\src\\utils\\1.py\"");
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String ret = in.readLine();
//            System.out.println("Python script output: " + ret);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
