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
        System.out.println(new Exception01().m());
    }
}
