package 每周20道力扣.第六周;

import java.util.Map;
import java.util.Scanner;

public class 完全背包 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();
        int V = scanner.nextInt();
        int[] w = new int[N];
        int[] v = new int[N];
        for (int i = 0; i < N; i++) {
            w[i] = scanner.nextInt();
            v[i] = scanner.nextInt();
        }
        System.out.println(packageAllCompact(N, V, w, v));
    }

    public static int packageAll(int M, int W, int[] weight, int[] value) {
        int[][] dp = new int[M][W + 1];
        // 初始化第一行
        for (int j = weight[0]; j <= W; j++) {
            dp[0][j] = j / weight[0] * value[0];
        }
        for (int i = 1; i < M; i++) {
            for (int j = 0; j <= W; j++) {
                // 序号为i的物品用k次
                for (int k = 0; k <= j / value[i]; k++) {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - k * weight[i]] + k * value[i]);
                }
            }
        }
        return dp[M - 1][W];
    }

    public static int packageAllCompact(int M, int W, int[] weight, int[] value) {
        int[] dp = new int[W + 1];
        for (int i = 0; i < M; i++) {
            for (int j = weight[i]; j <= W; j++) {
                dp[j] = Math.max(dp[j], dp[j - weight[i]] + value[i]);
            }
        }
        return dp[W];
    }
}
