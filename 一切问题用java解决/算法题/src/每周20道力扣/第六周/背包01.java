package 每周20道力扣.第六周;

import java.util.Scanner;

public class 背包01 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int M = scanner.nextInt();
        int W = scanner.nextInt();
        int[] weight = new int[M];
        int[] value = new int[M];
        for (int i = 0; i < M; i++) {
            weight[i] = scanner.nextInt();
        }
        for (int i = 0; i < M; i++) {
            value[i] = scanner.nextInt();
        }
        int maxW = package01(M, W, weight, value);
        System.out.println(maxW);
        maxW = package01Compact(M, W, weight, value);
        System.out.println(maxW);
    }

    public static int package01(int M, int W, int[] weight, int[] value) {
        int[][] dp = new int[M][W + 1];
        // 初始化第一行，第一列不用初始化了，因为都是0，jvm以及帮我们初始化了
        for (int i = weight[0]; i <= W; i++) {
            dp[0][i] = value[0];
        }
        for (int i = 1; i < M; i++) {
            for (int j = 1; j <= W; j++) {
                if (j - weight[i] >= 0) {
                    dp[i][j] = Math.max(dp[i - 1][j - weight[i]] + value[i], dp[i - 1][j]);
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }
        return dp[M - 1][W];
    }

    public static int package01Compact(int M, int W, int[] weight, int[] value) {
        int[] dp = new int[W + 1];
        for (int i = 0; i < M; i++) {
            for (int j = W; j >= weight[i]; j--) {
                dp[j] = Math.max(dp[j - weight[i]] + value[i], dp[j]);
            }
        }
        return dp[W];
    }
}
