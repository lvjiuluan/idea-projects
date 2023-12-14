//package 每周20道力扣.链表排序;
//
//public class 归并排序 {
//    public static ListNode generateList(int[] vals) {
//        ListNode head = null;
//        ListNode p = new ListNode(vals[0]);
//        head = p;
//        for (int i = 1; i < vals.length; i++) {
//            p.next = new ListNode(vals[i]);
//            p = p.next;
//        }
//        return head;
//    }
//
//    public static void printList(ListNode head) {
//        while (head != null) {
//            System.out.print(head.val + " ");
//            head = head.next;
//        }
//        System.out.println();
//    }
//
//    private static ListNode sortList(ListNode head) {
//        if (head == null || head.next == null) return head;
//        head = sortList(head);
//        ListNode secondHead = split(head);
//        return mergeSort(head, secondHead);
//    }
//
//    private static ListNode mergeSort(ListNode head, ListNode secondHead) {
//    }
//
//    private static ListNode split(ListNode head) {
//        ListNode slow = head;
//        ListNode fast = head.next;
//        while (fast != null && fast.next != null) {
//            slow = slow.next;
//            fast = fast.next.next;
//        }
//        ListNode secondHead = slow.next;
//        slow.next = null;
//        return secondHead;
//    }
//
//    public static void main(String[] args) {
//        int[] vals = {-1, 5, 3, 4, 0};
//        ListNode head = generateList(vals);
//        printList(head);
//        head = sortList(head);
//        printList(head);
//    }
//
//
//}
