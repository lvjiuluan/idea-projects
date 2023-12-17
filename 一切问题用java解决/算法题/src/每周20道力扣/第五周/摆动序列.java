package 每周20道力扣.第五周;

public class 摆动序列 {
    public static int wiggleMaxLength(int[] nums) {
        for (int i = 0; i < nums.length - 1; i++) {
            nums[i] = nums[i + 1] - nums[i];
            if (nums[i] > 0) nums[i] = 1;
            else if (nums[i] < 0) nums[i] = -1;
        }
        int res = 0;
        int pre = -nums[0];
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] == 0) continue;
            if (nums[i] != pre) {
                res++;
                pre = nums[i];
            }
        }
        return res + 1;
    }

    public static void main(String[] args) {
        int[] nums = {0,0,0};
        System.out.println(wiggleMaxLength(nums));
    }
}
