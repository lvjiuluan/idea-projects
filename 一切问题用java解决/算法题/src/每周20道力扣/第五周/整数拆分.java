package 每周20道力扣.第五周;

public class 整数拆分 {
    public static int integerBreak(int n) {
        int k = 2;
        double max = -1;
        for (int i = 2; i <= n; i++) {
            double t = Math.pow(n / (i *1.0), i*1.0);
            if (t > max) {
                {
                    System.out.println(t + "," + i);
                    max = t;
                    k = i;
                }
            }
        }
        return k;
    }

    public static void main(String[] args) {
        System.out.println(integerBreak(10));
    }
}
