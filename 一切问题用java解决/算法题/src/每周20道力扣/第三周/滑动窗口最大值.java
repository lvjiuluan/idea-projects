package 每周20道力扣.第三周;

import java.util.ArrayDeque;
import java.util.Deque;

public class 滑动窗口最大值 {
    public int[] maxSlidingWindow(int[] nums, int k) {
        Deque<Integer> deque = new ArrayDeque<>();
        int[] result = new int[nums.length - k + 1];
        for (int j = 0, i = 1 - k; j < nums.length; j++, i++) {
            if (i > 0 && deque.peekFirst() == nums[i - 1]) deque.pollFirst();
            while (!deque.isEmpty() && deque.peekLast() < nums[j]) deque.pollLast();
            deque.addLast(nums[j]);
            if (i >= 0) result[i] = deque.peekFirst();
        }
        return result;
    }
}
