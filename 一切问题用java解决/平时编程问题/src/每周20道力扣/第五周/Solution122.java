package 每周20道力扣.第五周;

public class Solution122 {
    public int maxProfit(int[] prices) {
        int n = prices.length;
        int result = 0;
        for (int i = 1; i < n; i++) {
            if (prices[i] - prices[i - 1] > 0) {
                result += prices[i] - prices[i - 1];
            }
        }
        return result;
    }



    public int maxProfit(int[] prices, int fee) {
        int n = prices.length;
        int[][] dp = new int[n][2];
        dp[0][0] = 0;
        dp[0][1] = -prices[0];
        for (int i = 1; i < n; i++) {
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i] - fee);
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] - prices[i]);
        }
        return dp[n - 1][0];
    }

    public static void main(String[] args) {
        Solution122 solution122 = new Solution122();
        int[] prices = {3, 2, 6, 5, 0, 3};
        int max = solution122.maxProfit(prices);
        System.out.println(max);
    }
}
