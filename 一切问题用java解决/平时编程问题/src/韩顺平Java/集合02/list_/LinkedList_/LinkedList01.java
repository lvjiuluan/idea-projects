package 韩顺平Java.集合02.list_.LinkedList_;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class LinkedList01 {
    public static void main(String[] args) {
        List list = new LinkedList();

        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        list.get(2);
    }
}
