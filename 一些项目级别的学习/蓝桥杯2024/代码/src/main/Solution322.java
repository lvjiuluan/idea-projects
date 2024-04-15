package main;

/*
 *
 * 给你一个整数数组 coins ，表示不同面额的硬币；以及一个整数 amount ，表示总金额。
 * 计算并返回可以凑成总金额所需的 最少的硬币个数 。
 * 如果没有任何一种硬币组合能组成总金额，返回 -1 。
 * 你可以认为每种硬币的数量是无限的。
 * */

import java.util.LinkedList;
import java.util.Queue;

public class Solution322 {
    public int coinChange(int[] coins, int amount) {
        Queue<QueueNode> queue = new LinkedList();
        queue.add(new QueueNode(amount, 0));
        while (!queue.isEmpty()) {
            QueueNode parent = queue.poll();
            if (parent.num == 0) {
                return parent.layer;
            }
            if (parent.num < 0) {
                return -1;
            }
            for (int i = 0; i < coins.length; i++) {
                queue.add(new QueueNode(parent.num - coins[i], parent.layer + 1));
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] coins = {2};
        System.out.println(new Solution322().coinChange(coins,11));
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
