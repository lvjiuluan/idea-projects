package 每周20道力扣.第一周;

public class 长度最小的子数组_209 {
    public static boolean judge(int[] nums, int length, int target) {
        System.out.println(length);
        int i = 0;
        int j = i + length - 1;
        int sums = 0;
        for (int k = i; k <= j; k++) {
            sums += nums[k];
        }
        while (j < nums.length) {
            if (sums >= target) return true;
            sums -= nums[i++];
            j++;
            if (j < nums.length) {
                sums += nums[j];
            }
        }
        return false;
    }

    public static int minSubArrayLen(int target, int[] nums) {
        int l = 1;
        int r = nums.length;
        while (l < r) {
            int mid = (l + r) >> 1;
            if (judge(nums, mid, target)) {
                r = mid;
            } else {
                l = mid + 1;
            }
        }
        return judge(nums, l, target) ? l : 0;
    }

    public static void main(String[] args) {
        int[] nums = {1,4,4};
        System.out.println(minSubArrayLen(4,nums));
    }
}
