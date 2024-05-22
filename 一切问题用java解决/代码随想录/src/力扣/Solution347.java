package 力扣;

import java.util.*;

public class Solution347 {
    public static void main(String[] args){
        int[] nums = {1,1,1,2,2,3};
        int k = 2;
        int[] res = topKFrequent(nums,k);
        System.out.println(Arrays.toString(res));
    }
    public static int[] topKFrequent(int[] nums, int k) {
        List<Integer> res = new ArrayList<>();
        Map<Integer,Integer> map = new HashMap<>();
        for(int num : nums){
            Integer count = map.get(num);
            if(count == null) count = 0;
            count++;
            map.put(num,count);
        }
        Integer[] bucket = new Integer[nums.length + 1];
        for(Map.Entry<Integer,Integer> entry : map.entrySet()){
            bucket[entry.getValue()] = bucket[entry.getKey()];
        }
        for(int i = bucket.length - 1; i >=0; i--){
            if(bucket[i] == null) continue;
            res.add(bucket[i]);
            if(res.size() == k) break;
        }
        return res.stream().mapToInt(i->i).toArray();
    }
}
