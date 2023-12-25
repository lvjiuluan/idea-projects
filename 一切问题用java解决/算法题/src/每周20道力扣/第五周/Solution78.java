package 每周20道力扣.第五周;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution78 {
    List<List<Integer>> lists = new ArrayList<>();
    List<Integer> list = new ArrayList<>();
    int[] nums;

    public void dfs(int i) {
        lists.add(new ArrayList<>(list));
        for (int j = i + 1; j < nums.length; j++) {
            if (j > i + 1 && nums[j] == nums[j - 1]) continue;
            list.add(nums[j]);
            dfs(j);
            list.remove(list.size() - 1);
        }
    }

    public List<List<Integer>> subsets(int[] nums) {
        Arrays.sort(nums);
        this.nums = nums;
        dfs(-1);
        return lists;
    }

    public static void main(String[] args) {
        Solution78 solution78 = new Solution78();
        int[] nums = {4,4,4,1,4};
        System.out.println(solution78.subsets(nums));
    }
}
