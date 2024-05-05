package 每周20道力扣.第七周;

import java.util.LinkedList;
import java.util.Queue;

public class Solution322 {
    public static void main(String[] args) {
        int[] coins = {1, 2, 5};
        int c = new Solution322().coinChange(coins, 100);
        System.out.println(c);

    }

    public int coinChange(int[] coins, int amount) {
        Queue<QueueNode> queue = new LinkedList();
        boolean[] visited = new boolean[amount + 1];
        queue.add(new QueueNode(amount, 0));
        visited[amount] = true;
        while (!queue.isEmpty()) {
            QueueNode parent = queue.poll();
            if (parent.num == 0) {
                return parent.layer;
            }
            if (parent.num < 0) {
                return -1;
            }
            for (int i = 0; i < coins.length; i++) {
                if (coins[i] <= parent.num && !visited[parent.num - coins[i]]) {
                    queue.add(new QueueNode(parent.num - coins[i], parent.layer + 1));
                    visited[parent.num - coins[i]] = true;
                }
            }
        }
        return -1;
    }
}

class QueueNode {
    int num;
    int layer;

    public QueueNode(int num, int layer) {
        this.num = num;
        this.layer = layer;
    }

    @Override
    public String toString() {
        return "QueueNode{" +
                "num=" + num +
                ", layer=" + layer +
                '}';
    }
}