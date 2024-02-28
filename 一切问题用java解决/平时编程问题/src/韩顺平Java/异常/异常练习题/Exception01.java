package 韩顺平Java.异常.异常练习题;

public class Exception01 {

    public int m() {
        int i = 1;
        try {
            i++;
            int k = 1 / 0;
            return 1;
        } catch (Exception e) {
            return 2;
        } finally {
            return 4;
        }
    }

    public static void main(String[] args) {
        String ss = "h" + "g";
        "aaa".length();
        "aaa".hashCode();
        String b = "hhh";
        String s1 = new String("hhh");
        String a = new String("aa") + "bb";
    }
}
