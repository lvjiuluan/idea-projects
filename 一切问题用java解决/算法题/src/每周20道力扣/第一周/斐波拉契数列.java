package 每周20道力扣.第一周;

public class 斐波拉契数列 {
    public static int f(int n) {
        if (n == 1 || n == 2) return 1;
        return f(n - 1) + f(n - 2);
    }

    public static void main(String[] args) {
        for (int i = 1; i < 10; i++) {
            System.out.println(f(i));
        }
    }
}
