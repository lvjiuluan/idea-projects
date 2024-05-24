import java.util.Queue;
import java.util.LinkedList;

public class MoneyChange3 {
	public static void main(String args[]){
		
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