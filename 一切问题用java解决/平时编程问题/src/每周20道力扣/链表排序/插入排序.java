package 每周20道力扣.链表排序;

public class 插入排序 {
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

    public static ListNode getMin(ListNode head) {
        int min = head.val;
        ListNode p = head;
        while (head != null) {
            if (head.val < min) {
                p = head;
                min = head.val;
            }
            head = head.next;
        }
        return p;
    }

    public static ListNode remove(ListNode head, ListNode p) {
        ListNode pre = head;
        if (pre == p) {
            head = head.next;
            pre.next = null;
            return head;
        }
        while (pre.next != p) {
            pre = pre.next;
        }
        pre.next = p.next;
        p.next = null;
        return head;
    }

    public static ListNode getTail(ListNode head) {
        while (head != null && head.next != null) head = head.next;
        return head;
    }

    public static ListNode insertSort(ListNode head) {
        ListNode p = new ListNode();
        ListNode q = null;
        while (head != null) {
            q = getMin(head);
            head = remove(head, q);
            getTail(p).next = q;
        }
        return p.next;
    }

    public static void main(String[] args) {
        int[] vals = {-1,5,3,4,0};
        ListNode head = generateList(vals);
        printList(head);
        head = insertSort(head);
        printList(head);
    }
}

class ListNode {
    int val;
    ListNode next;

    public ListNode() {
    }

    public ListNode(int val) {
        this.val = val;
    }
}