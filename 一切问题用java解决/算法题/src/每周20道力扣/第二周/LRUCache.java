package 每周20道力扣.第二周;

import java.util.Map;
import java.util.HashMap;

class LRUCache {
    int capacity;
    Map<Integer, ListNode> map = new HashMap<>();
    ListNode head;
    ListNode tail;

    public LRUCache(int capacity) {
        this.capacity = capacity;
    }

    public int get(int key) {
        ListNode p = map.get(key);
        if (p == null) return -1;
        p = setHead(key,p.val);
        map.put(key, p);
        return p.val;
    }

    public void put(int key, int value) {
        ListNode p = null;
        if (get(key) == -1) {
            p = insertHead(key, value);
        } else {
            p = setHead(key, value);
        }
        map.put(key, p);
    }

    public void remove() {
        map.remove(tail.key);
        if (tail.pre == null) {
            head = null;
            tail = null;
        } else {
            ListNode pre_tail = tail.pre;
            pre_tail.next = null;
            tail = pre_tail;
        }
    }

    public ListNode insertHead(int key, int value) {
        if (map.size() == capacity) {
            remove();
        }
        ListNode p = new ListNode(key, value);
        if (head == null) {
            head = p;
            tail = head;
            return p;
        }
        p.next = head;
        head.pre = p;
        head = p;
        return p;
    }

    public ListNode setHead(int key, int val) {
        ListNode p = map.get(key);
        p.val = val;
        if (p == head) return p;
        if (p == tail) tail = tail.pre;
        ListNode pre_p = p.pre;
        ListNode next_p = p.next;
        if (pre_p != null) pre_p.next = next_p;
        if (next_p != null) next_p.pre = pre_p;
        p.next = head;
        head.pre = p;
        p.pre = null;
        head = p;
        return p;
    }

    public void printList() {
        ListNode p = head;
        while (p != null) {
            System.out.print(p.key + " ");
            p = p.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        //["LRUCache","put","put","get","put","put","get"]
        //[[2],[2,1],[2,2],[2],[1,1],[4,1],[2]]
        LRUCache lru = new LRUCache(2);
        lru.put(2, 1);
        lru.put(2, 2);
        lru.get(2);
        lru.printList();
        lru.put(1, 1);
        lru.printList();
        lru.put(4, 1);
        lru.printList();
        lru.get(2);
        lru.printList();
    }
}

class ListNode {
    int val;
    int key;
    ListNode pre;
    ListNode next;

    public ListNode() {
    }

    public ListNode(int val) {
        this.val = val;
    }

    public ListNode(int key, int val) {
        this.val = val;
        this.key = key;
    }
}