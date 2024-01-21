package 每周20道力扣.第五周;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class Solution322 {
    public int coinChange(int[] coins, int amount) {
        Arrays.sort(coins);
        Queue<Integer> queue = new ArrayDeque<>();
        boolean[] visited = new boolean[amount + 1];
        queue.add(amount);
        int step = 1;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                Integer head = queue.poll();
                for (int coin : coins) {
                    int next = head - coin;
                    if (next == 0) {
                        return step;
                    }
                    if (next < 0) {
                        break;
                    }
                    if (visited[next]) {
                        visited[next] = true;
                        queue.offer(next);
                    }
                }
            }
            step++;
        }
        return step;
    }
}
