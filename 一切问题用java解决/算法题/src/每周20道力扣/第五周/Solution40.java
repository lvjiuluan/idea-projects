package 每周20道力扣.第五周;

import java.util.*;

public class Solution40 {
    LinkedList<Integer> list;
    List<List<Integer>> lists;
    Integer[] candidates;
    int n;
    int target;
    int[] visited;

    public void dfs(int i, int sum) {
        if (i <= n + 1) {
            if (sum == target) {
                List<Integer> temp = new ArrayList(list);
                if (!lists.contains(temp)) {
                    System.out.println(temp);
                    lists.add(temp);
                }
            }
            for (int j = i; j < n; j++) {
                if (sum + candidates[j] <= target) {
                    list.addLast(candidates[j]);
                    dfs(j + 1, sum + candidates[j]);
                    list.removeLast();
                }
            }
        }
    }

    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        Set<Integer> set = new HashSet<>();
        for (int candidate : candidates) {
            set.add(candidate);
        }
        Arrays.sort(candidates);
        this.candidates = set.toArray(new Integer[0]);
        this.n = this.candidates.length;
        this.target = target;
        visited = new int[candidates.length];
        list = new LinkedList<>();
        lists = new ArrayList<>();
        dfs(0, 0);
        return lists;
    }

    public static void main(String[] args) {
        Solution40 solution40 = new Solution40();
        int[] candidates = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
        int target = 30;
        solution40.combinationSum2(candidates,target);
    }
}
