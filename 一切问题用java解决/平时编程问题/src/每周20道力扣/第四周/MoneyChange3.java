package 每周20道力扣.第四周;

import java.util.LinkedList;
import java.util.Queue;

public class MoneyChange3 {
    public static void main(String[] args) {
        /*int[] coins = {5,2,1};
        int amount = 11;
        System.out.println(coinChange(coins,amount));*/

        String s = "abc";

        System.out.println(s.charAt(1) + "sad");
    }
    public static int coinChange(int[] coins, int amount) {
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[amount + 1];
        queue.add(amount);
        visited[amount] = true;
        int res = 0;
        while(!queue.isEmpty()){
            int size = queue.size();
            for(int i = 0; i < size; i++){
                Integer head = queue.poll();
                if(head == 0) {
                    return res;
                }
                for(int coin : coins){
                    int diff = head - coin;
                    if(diff >= 0 && visited[diff] == false){
                        visited[diff] = true;
                        queue.add(diff);
                    }
                }
            }
            res++;
        }
        return -1;
    }
}
