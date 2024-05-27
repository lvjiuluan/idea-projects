package 每周20道力扣.第四周;

public class 背包问题 {
    public static void main(String[] args) {
        int[] weight = {1, 3, 4};
        int[] value = {15, 20, 30};
        int W = 4;
        int n = weight.length;
        int value1 = maxValue1(W, n, weight, value);
        System.out.println(value1);
        System.out.println(maxValue2(W, n, weight, value));
    }

    // 01背包
    public static int maxValue1(int W, int n, int[] weight, int[] value) {
        int[] dp = new int[W + 1];
        for (int i = 0; i < n; i++) {
            for (int j = W; j >= weight[i]; j--) {
                dp[j] = Math.max(dp[j], dp[j - weight[i]] + value[i]);
            }
        }
        return dp[W];
    }

    // 完全背包
    public static int maxValue2(int W, int n, int[] weight, int[] value) {
        int[] dp = new int[W + 1];
        for (int i = 0; i < n; i++) {
            for (int j = weight[i]; j <= W; j++) {
                dp[j] = Math.max(dp[j], dp[j - weight[i]] + value[i]);
            }
        }
        return dp[W];
    }
}
