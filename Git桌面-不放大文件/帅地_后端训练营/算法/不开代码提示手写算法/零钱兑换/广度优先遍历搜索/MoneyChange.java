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
class MoneyChange{
	public static void main(String[] args){
		MoneyChange moneyChange = new MoneyChange();
		int[] coins = {1};
		int amount = 0;
		int result = moneyChange.coinChange(coins,amount);
		System.out.println(result);
	}
	public int coinChange(int[] coins, int amount) {
		Queue<Node> q = new LinkedList();
		q.add(new Node(amount,0));
		boolean[] visited = new boolean[amount + 1];
		while(!q.isEmpty()){
			Node parent = q.poll();
			int number = parent.number;
			int layer = parent.layer;
			if(number == 0){
				return layer;
			}
			for(int coin : coins){
				if(number - coin >= 0 && !visited[number - coin]){
					visited[number - coin] = true;
					q.add(new Node(number - coin, layer + 1));
				}
			}
		}
		return -1;
    }
}

class Node{
	int number;
	int layer;
	
	public Node(int number, int layer){
		this.number = number;
		this.layer = layer;
	}
}