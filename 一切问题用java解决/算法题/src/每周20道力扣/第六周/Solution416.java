package 每周20道力扣.第六周;


public class Solution416 {
    public boolean canPartition(int[] nums) {
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        if (sum % 2 != 0) return false;
        sum /= 2;
        int[] dp = new int[sum + 1];
        for (int i = 0; i < nums.length; i++) {
            for (int j = sum; j >= nums[i]; j--) {
                dp[j] = Math.max(dp[j], dp[j - nums[i]] + nums[i]);
            }
        }
        return dp[sum] == sum;
    }

    public static void main(String[] args) {
        Solution416 solution416 = new Solution416();
        int[] nums = {22, 2};
        System.out.println(solution416.canPartition(nums));
    }
}
