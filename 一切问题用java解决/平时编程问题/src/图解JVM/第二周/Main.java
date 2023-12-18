package 图解JVM.第二周;

import java.util.Comparator;
import java.util.PriorityQueue;

public class Main {
    public static void main(String[] args) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>((a, b) -> b - a);
        minHeap.offer(2);
        minHeap.offer(1);
        minHeap.offer(-1);
        System.out.println(minHeap.poll());
    }
}
