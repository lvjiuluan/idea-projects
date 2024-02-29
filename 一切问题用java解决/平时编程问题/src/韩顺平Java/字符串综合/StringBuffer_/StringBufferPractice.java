package 韩顺平Java.字符串综合.StringBuffer_;

public class StringBufferPractice {

    public static String convert(double price) {
        StringBuffer sb = new StringBuffer(String.valueOf(price));
        int pointIndex = sb.indexOf(".");
        for (int i = 3; i < pointIndex; i += 4) {
            sb.insert(i, ",");
        }
        return sb.toString();
    }

    public static void main(String[] args) {

    }
}
