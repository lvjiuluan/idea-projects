package 每周20道力扣.第六周;

public class Solution322 {
    public int CompletePack(int[] w, int V) {
        int n = w.length;
        int[] dp = new int[V + 1];
        for (int i = 0; i < n; i++) {
            for (int j = V; j >= V - w[i]; j--) {
                dp[j] = Math.max(dp[j], dp[j - w[i]] + w[i]);
            }
        }
        return dp[V];
    }

    public int Pack01(int[] value, int[] weight, int W) {
        int n = value.length;
        int[][] dp = new int[n][W + 1];
        for (int i = 1; i < n; i++) {
            for (int j = 1; j <= W; j++) {
                if (j >= weight[i]) {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - weight[i]] + value[i]);
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }
        return dp[n - 1][W];
    }

    public int Pack01_02(int[] value, int[] weight, int W) {
        int n = value.length;
        int[] dp = new int[W + 1];
        for (int i = 0; i < n; i++) {
            for (int j = weight[i]; j <= W; j++) {
                dp[j] = Math.max(dp[j], dp[j - weight[i]] + value[i]);
            }
        }
        return dp[W];
    }

    public static void main(String[] args) {
        int[] value = {15, 20, 30};
        int[] weight = {1, 3, 4};
        int W = 4;
        System.out.println(new Solution322().Pack01(value, weight, W));
    }
}
