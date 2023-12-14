package 每周20道力扣.第三周;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class 滑动窗口最大值 {
    public static int[] maxSlidingWindow(int[] nums, int k) {
        int[] result = Arrays.copyOfRange(nums, 0, k);
        List<Integer> resultList = new ArrayList<>();
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);
        for (int r : result) {
            maxHeap.add(r);
        }
        int i = 0;
        int j = k - 1;
        while (j < nums.length) {
            resultList.add(maxHeap.peek());
            maxHeap.remove(nums[i++]);
            if (j + 1 < nums.length) maxHeap.add(nums[++j]);
            else ++j;
        }
        result = new int[resultList.size()];
        for (i = 0; i < resultList.size(); i++) {
            result[i] = resultList.get(i);
        }
        return result;
    }

    public static void main(String[] args) {
        int[] nums = {1,3,-1,-3,5,3,6,7};
        for (int r : maxSlidingWindow(nums, 3)) {
            System.out.print(r + " ");
        }
    }
}
