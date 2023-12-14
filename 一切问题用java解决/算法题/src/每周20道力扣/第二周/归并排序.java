package 每周20道力扣.第二周;

import javax.swing.plaf.InsetsUIResource;
import java.util.Random;

public class 归并排序 {
    public static ListNode generteList(int[] vals) {
        ListNode head = new ListNode();
        ListNode tail = head;
        for (int val : vals) {
            tail.next = new ListNode(val);
            tail = tail.next;
        }
        return head.next;
    }

    public static void printList(ListNode head) {
        while (head != null) {
            System.out.print(head.val + " ");
            head = head.next;
        }
        System.out.println();
    }

    private static ListNode sortList(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode secondHead = split(head);
        head = sortList(head);
        secondHead = sortList(secondHead);
        return mergeList(head, secondHead);
    }

    public static ListNode getTail(ListNode head) {
        while (head.next != null) {
            head = head.next;
        }
        return head;
    }

    public static void insert(ListNode head, ListNode p) {
        ListNode tail = getTail(head);
        tail.next = p;
    }

    private static ListNode mergeList(ListNode list1, ListNode list2) {
        if (list1 == null || list2 == null) return list1 == null ? list2 : list1;
        ListNode head = new ListNode();
        ListNode tail = head;
        while (list1 != null && list2 != null) {
            while (list1 != null && list2 != null && list1.val <= list2.val) {
                tail.next = list1;
                list1 = list1.next;
                tail = tail.next;
            }
            while (list1 != null && list2 != null && list1.val > list2.val) {
                tail.next = list2;
                list2 = list2.next;
                tail = tail.next;
            }
        }
        if (list1 == null) tail.next = list2;
        else tail.next = list1;
        return head.next;
    }

    private static ListNode split(ListNode head) {
        ListNode slow = head;
        ListNode fast = head.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        ListNode newHead = slow.next;
        slow.next = null;
        return newHead;
    }

    public static void main(String[] args) {
//        int[] vals = {5, 3, 2, 1, 4, 48, 32, 2, 21, 3421, 32, 34};
//        ListNode head = generteList(vals);
//        printList(head);
//        head = sortList(head);
//        printList(head);
        Random rand = new Random();
        System.out.println(rand.nextInt(1) + 1);
    }


}

//class ListNode {
//    int val;
//    ListNode next;
//
//    public ListNode() {
//    }
//
//    public ListNode(int val) {
//        this.val = val;
//    }
//}