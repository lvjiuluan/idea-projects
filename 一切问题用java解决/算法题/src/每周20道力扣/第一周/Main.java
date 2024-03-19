package 每周20道力扣.第一周;

import java.util.Scanner;

public class Main {
    public static int package01(int W, int[] weight, int[] value) {
        int n = weight.length;
        int[][] dp = new int[n][W + 1];

        // i号物品可选，背包容量为0时初始化

        // 0号物品可选，背包容量为j时初始化
        for (int j = weight[0]; j < W + 1; j++) {
            dp[0][j] = value[0];
        }

        for (int i = 1; i < n; i++) {
            for (int j = 1; j < W + 1; j++) {
                if (j >= weight[i]) {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - weight[i]] + value[i]);
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }
        return dp[n - 1][W];
    }

    public static int package01Compact(int W, int[] weight, int[] value) {
        int n = weight.length;
        int[] dp = new int[W + 1];

        for (int i = 0; i < n; i++) {
            for (int j = W; j >= weight[i]; j--) {
                dp[j] = Math.max(dp[j], dp[j - weight[i]] + value[i]);
            }
        }
        return dp[W];
    }

    public static int packageComplete(int W, int[] weight, int[] value) {
        /*
         * 如果用二维dp，这里会用三重循环，中间，每一次都要判断之前的要不要加
         * */
        return 0;
    }

    public static int packageCompleteCompact(int W, int[] weight, int[] value) {
        int n = weight.length;
        int[] dp = new int[W + 1];
        for (int i = 0; i < n; i++) {
            for (int j = weight[i]; j <= W; j++) {
                dp[j] = Math.max(dp[j], dp[j - weight[i]] + value[i]);
            }
        }
        return dp[W];
    }

    public static void main(String[] args) {
        int N, V;
        Scanner scanner = new Scanner(System.in);
        N = scanner.nextInt();
        V = scanner.nextInt();
        int[] weight = new int[N];
        int[] value = new int[N];
        for (int i = 0; i < N; i++) {
            weight[i] = scanner.nextInt();
            value[i] = scanner.nextInt();
        }
        System.out.println(packageCompleteCompact(V, weight, value));
    }
}
