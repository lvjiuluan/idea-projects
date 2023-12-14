package 每周20道力扣.第四周;

import java.util.ArrayList;
import java.util.List;

public class 只出现一次的数字 {
    static List<Integer> list1 = new ArrayList<>();
    static List<Integer> list2 = new ArrayList<>();

    public static int[] singleNumber(int[] nums) {
        int xor = 0;
        for (int num : nums) {
            xor ^= num;
        }
        int mask = xor & (-xor);
        int[] ans = new int[2];
        for (int num : nums) {
            if ((num & mask) == 0) {
                ans[0] ^= num;
            } else {
                ans[0] ^= num;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] nums = {1, 2, 1, 3, 2, 5};
//        singleNumber(nums);
//        System.out.println(list1);
//        System.out.println(list2);
        int two = 1;
        System.out.println( 1 & ~two);
    }
}
