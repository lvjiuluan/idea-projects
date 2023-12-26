package 每周20道力扣.第六周;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution46 {
    List<List<Integer>> lists = new ArrayList<>();
    List<Integer> list = new ArrayList<>();
    int[] visited = new int[7];
    int[] nums;

    public void dfs(int i) {
        if (list.size() == nums.length) System.out.println(list);
        for (int j = 0; j < nums.length; j++) {
            if (visited[j] == 1) continue;
            if(j > 0 && visited[j-1] == 0 && nums[j] == nums[j - 1]) continue;
            visited[j] = 1;
            list.add(nums[j]);
            dfs(j);
            list.remove(list.size() - 1);
            visited[j] = 0;
        }
    }

    public List<List<Integer>> permute(int[] nums) {
        Arrays.sort(nums);
        this.nums = nums;
        dfs(0);
        return lists;
    }

    public static void main(String[] args) {
        Solution46 solution46 = new Solution46();
        int[] nums = {-1,2,-1,2,1,-1,2,1};
        solution46.permute(nums);
    }
}
