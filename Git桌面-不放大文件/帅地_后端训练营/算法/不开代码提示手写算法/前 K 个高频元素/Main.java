import java.util.Map;
import java.util.HashMap;
import java.util.Queue;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.Arrays;

class Main{
	public static void main(String[] args){
		int[] nums = {1,1,1,2,2,3};
		int k = 2;
		int[] res = topKFrequent(nums,k);
		System.out.println(Arrays.toString(res));
	}
	
	public static int[] topKFrequent(int[] nums, int k) {
		
		int[] res = new int[k];
		Map<Integer,Integer> map = new HashMap<>();
		for(int num : nums){
			Integer count = map.get(num);
			if(count == null) count = 0;
			count++;
			map.put(num,count);
		}
		/*
		Queue<Integer> minHeap = new PriorityQueue<>(new Comparator<Integer>(){
			@Override
			public int compare(Integer i1, Integer i2){
				return map.get(i1) - map.get(i2);
			}
		});
		*/
		
		// Queue<Integer> minHeap = new PriorityQueue<>((a,b) -> map.get(a)-map.get(b));
		
		Queue<Integer> minHeap = new PriorityQueue<>(Comparator.comparing(map::get));
		
		for(Integer key : map.keySet()){
			if(minHeap.size() < k){
				minHeap.add(key);
			}else{
				Integer count = map.get(key);
				Integer countHead = map.get(minHeap.peek());
				if(count > countHead){
					minHeap.remove();
					minHeap.add(key);
				}
			}
		}
		for(int i = 0; i < k; i++){
			res[i] = minHeap.poll();
		}
		
		return res;
    }
}