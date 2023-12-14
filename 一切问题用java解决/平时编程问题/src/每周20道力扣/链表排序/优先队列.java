package 每周20道力扣.链表排序;

import java.util.PriorityQueue;

public class 优先队列 {
    public static ListNode generateList(int[] vals) {
        ListNode head = null;
        ListNode p = new ListNode(vals[0]);
        head = p;
        for (int i = 1; i < vals.length; i++) {
            p.next = new ListNode(vals[i]);
            p = p.next;
        }
        return head;
    }

    public static void printList(ListNode head) {
        while (head != null) {
            System.out.print(head.val + " ");
            head = head.next;
        }
        System.out.println();
    }

    public static ListNode heapSort(ListNode head) {
        PriorityQueue<ListNode> heap = new PriorityQueue<>((a, b) -> a.val - b.val);
        while (head != null) {
            heap.offer(head);
            head = head.next;
        }
        ListNode dummy = new ListNode();
        ListNode tail = dummy;
        while (!heap.isEmpty()) {
            tail.next = heap.poll();
            tail = tail.next;
        }
        tail.next = null;
        return dummy.next;
    }

    public static void main(String[] args) {

        int[] vals = {-1, 5, 3, 4, 0};
        ListNode head = generateList(vals);
        printList(head);
        head = heapSort(head);
        printList(head);
    }
}
