package 每周20道力扣.第二周;

import java.util.ArrayDeque;
import java.util.Queue;

class MyStack {
    Queue<Integer> queue1 = new ArrayDeque<>();
    Queue<Integer> queue2 = new ArrayDeque<>();

    public MyStack() {

    }

    public void push(int x) {
        if (!queue1.isEmpty()) queue1.add(x);
        else queue2.add(x);
    }

    public int pop() {
        int top;
        if (!queue1.isEmpty()) {
            while (queue1.size() != 1) {
                queue2.add(queue1.poll());
            }
            top = queue1.poll();
        } else {
            while (queue2.size() != 1) {
                queue1.add(queue2.poll());
            }
            top = queue2.poll();
        }
        return top;
    }

    public int top() {
        int top;
        if (!queue1.isEmpty()) {
            while (queue1.size() != 1) {
                queue2.add(queue1.poll());
            }
            top = queue1.poll();
            queue2.add(top);
        } else {
            while (queue2.size() != 1) {
                queue1.add(queue2.poll());
            }
            top = queue2.poll();
            queue1.add(top);
        }
        return top;
    }

    public boolean empty() {
        if(queue2.isEmpty() && queue1.isEmpty()) return true;
        return false;
    }

    public static void main(String[] args) {
//        ["MyStack","push","push","top","pop","empty"]
//        [[],[1],[2],[],[],[]]
        MyStack myStack = new MyStack();
        myStack.push(1);
        myStack.push(2);
        System.out.println(myStack.top());
    }
}

/**
 * Your MyStack object will be instantiated and called as such:
 * MyStack obj = new MyStack();
 * obj.push(x);
 * int param_2 = obj.pop();
 * int param_3 = obj.top();
 * boolean param_4 = obj.empty();
 */
