package 每周20道力扣.第一周;

public class 二分查找_704 {
    public static int search(int[] nums, int target) {
        int l = 0;
        int r = nums.length - 1;
        while (l < r) {
            int mid = (l + r) >> 1;
            if (nums[mid] < target) l = mid + 1;
            else if (nums[mid] > target) r = mid;
            else return mid;
        }
        return nums[l] == target ? l : -1;
    }
}
