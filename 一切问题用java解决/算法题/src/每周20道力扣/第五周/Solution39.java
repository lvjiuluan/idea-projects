package 每周20道力扣.第五周;


import java.util.ArrayList;
import java.util.List;

public class Solution39 {
    List<List<Integer>> lists;
    List<Integer> list;
    int target;
    int[] candidates;

    public void dfs(int i, int sum, int left) {
        if (sum + left < target) return;
        if (sum > target) return;
        if (sum == target) {
            lists.add(new ArrayList<>(list));
        }
        for (int j = i; j < candidates.length; j++) {
            if (j > i && candidates[j] == candidates[j - 1]) continue;
            list.add(candidates[j]);
            dfs(j, sum + candidates[j], left - candidates[j]);
            list.remove(list.size() - 1);
        }
    }

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        int left = 0;
        for (int candidate : candidates) {
            left += candidate;
        }
        list = new ArrayList<>();
        lists = new ArrayList<>();
        this.target = target;
        this.candidates = candidates;
        dfs(0, 0, left);
        return lists;
    }


    public static void main(String[] args) {
        Solution39 solution39 = new Solution39();
        int[] candidates = {2, 3};
        int target = 6;
        List<List<Integer>> lists = solution39.combinationSum(candidates, target);
        System.out.println(lists);
    }
}
