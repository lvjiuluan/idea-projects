package 每周20道力扣.第二周;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

class MedianFinder {
    List<Integer> nums = new ArrayList<>();
    int n = 0;
    PriorityQueue<Integer> minHeapA = new PriorityQueue<>((a, b) -> a - b);
    PriorityQueue<Integer> maxHeapB = new PriorityQueue<>((a, b) -> b - a);


    public MedianFinder() {

    }

    public void addNum(int num) {
        if (minHeapA.size() == maxHeapB.size()) {
            maxHeapB.offer(num);
            minHeapA.offer(maxHeapB.poll());
        } else {
            minHeapA.offer(num);
            maxHeapB.offer(minHeapA.poll());
        }
    }

    public double findMedian() {
        if (minHeapA.size() != maxHeapB.size()) return minHeapA.peek();
        else return (minHeapA.peek() + maxHeapB.peek()) / 2;
    }

    public static void main(String[] args) {
        MedianFinder obj = new MedianFinder();
        obj.addNum(2);
        obj.addNum(3);
        obj.addNum(1);
        obj.addNum(4);
        System.out.println(obj.findMedian());
    }
}

/**
 * Your MedianFinder object will be instantiated and called as such:
 * MedianFinder obj = new MedianFinder();
 * obj.addNum(num);
 * double param_2 = obj.findMedian();
 */
