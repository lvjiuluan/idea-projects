package 每周20道力扣.第二周;

import java.util.PriorityQueue;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;

public class 前K个高频元素 {
    public static int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap();
        PriorityQueue<Integer> minHeap = new PriorityQueue<>((a, b) -> a - b);
        for (int num : nums) {
            int freq = map.get(num) == null ? 0 : map.get(num);
            freq++;
            if (minHeap.size() < k) minHeap.offer(freq);
            else if (freq > minHeap.peek()) {
                minHeap.poll();
                minHeap.offer(freq);
            }
            map.put(num, freq);
        }
        int kThMaxFreq = minHeap.peek();
        int[] result = new int[k];
        int i = 0;
        for (int num : map.keySet()) if (map.get(num) >= kThMaxFreq) result[i++] = num;
        return result;
    }

    public static void main(String[] args) {
        int[] nums = {1, 1, 1, 2, 2, 3};
        topKFrequent(nums, 2);
    }
}

