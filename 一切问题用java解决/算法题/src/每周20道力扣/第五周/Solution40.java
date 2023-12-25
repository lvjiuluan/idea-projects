package 每周20道力扣.第五周;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Solution40 {
    LinkedList<Integer> list;
    List<List<Integer>> lists;
    int target;
    int[] candidates;

    public void dfs(int i, int sum) {
        if (sum > target) return;
        if (sum == target) {
            lists.add(new ArrayList<>(list));
            return;
        }
        for (int j = i + 1; j < candidates.length; j++) {
            if (j != i + 1 && candidates[j] == candidates[j - 1]) continue;
            list.addLast(candidates[j]);
            dfs(j, sum + candidates[j]);
            list.removeLast();
        }
    }

    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        Arrays.sort(candidates);
        this.candidates = candidates;
        this.target = target;
        list = new LinkedList<>();
        lists = new ArrayList<>();
        dfs(-1, 0);
        return lists;
    }

    public static void main(String[] args) {
        int[] candidates = {10, 1, 2, 7, 6, 1, 5};
        int target = 8;
        Solution40 solution40 = new Solution40();
        System.out.println(solution40.combinationSum2(candidates, target));
    }
}
