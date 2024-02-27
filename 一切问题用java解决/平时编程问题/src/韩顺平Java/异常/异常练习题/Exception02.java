package 韩顺平Java.异常.异常练习题;

public class Exception02 {
    public int m() {
        int i = 1;
        try {
            i++;
            int k = 1 / 0;
            return 1;
        } catch (Exception e) {
            return ++i;
        } finally {
            i++;
        }
    }

    public static void main(String[] args) {
        System.out.println(new Exception02().m());
    }
}
