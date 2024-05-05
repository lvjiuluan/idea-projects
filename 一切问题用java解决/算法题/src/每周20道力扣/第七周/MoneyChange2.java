package 每周20道力扣.第七周;

import java.util.Queue;
import java.util.LinkedList;

/*
11	1 2 5
11
11 10 9 6
10 9 6 8 5
9 6 8 5 7 4
6 8 5 7 4 1
8 5 7 4 1 3
5 7 4 1 3 0
*/
class MoneyChange2{
    public static void main(String[] args){
        MoneyChange2 moneyChange2 = new MoneyChange2();
        int[] coins = {1,2,5};
        int amount = 11;
        int result = moneyChange2.coinChange(coins,amount);
        System.out.println(result);
    }
    public int coinChange(int[] coins, int amount) {
        if(amount == 0 ) return 0;
        Queue<Integer> q = new LinkedList();
        q.add(amount);
        boolean[] visited = new boolean[amount + 1];
        int step = 1;
        while(!q.isEmpty()){
            int size = q.size();
            for(int i = 0; i < size; i++){
                int number = q.poll();
                for(int coin : coins){
                    if(number - coin == 0) return step;
                    if(number - coin > 0 && !visited[number - coin]){
                        visited[number - coin] = true;
                        q.add(number - coin);
                    }
                }
            }
            step++;
        }
        return -1;
    }
}