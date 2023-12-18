package 每周20道力扣.第一周;

public class 移除元素_27 {
    public static int removeElement(int[] nums, int val) {
        int cur = 0;
        int next = cur;
        while (next < nums.length) {
            while (next < nums.length && nums[next] == val) next++;
            nums[cur++] = nums[next++];
        }
        return cur;
    }

    public static void main(String[] args) {
        int[] nums = {2,3,4,1,1,5,6,6,1,1,3};
        int len = removeElement(nums, 1);
        for (int i = 0; i < len; i++) {
            System.out.print(nums[i] + " ");
        }
    }
}
