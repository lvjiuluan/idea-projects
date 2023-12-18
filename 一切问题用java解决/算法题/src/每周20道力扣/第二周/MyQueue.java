package 每周20道力扣.第二周;

import java.util.Stack;

class MyQueue {

    Stack<Integer> in = new Stack<>();
    Stack<Integer> out = new Stack<>();

    public MyQueue() {

    }

    public void push(int x) {
        in.push(x);
    }

    public int pop() {
        if (out.isEmpty()) {
            while (!in.isEmpty()) {
                out.push(in.pop());
            }
        }
        return out.pop();
    }

    public int peek() {
        if (out.isEmpty()) {
            while (!in.isEmpty()) {
                out.push(in.pop());
            }
        }
        return out.peek();
    }

    public boolean empty() {
        if (out.isEmpty()) {
            while (in.isEmpty()) {
                out.push(in.pop());
            }
        }
        return out.isEmpty();
    }

    public static void main(String[] args) {
        MyQueue obj = new MyQueue();
        for (int i = 0; i < 5; i++) {
            obj.push(i);
        }
        for (int i = 0; i < 6; i++) {
            System.out.println(obj.pop());
        }
    }
}

/**
 * Your MyQueue object will be instantiated and called as such:
 * MyQueue obj = new MyQueue();
 * obj.push(x);
 * int param_2 = obj.pop();
 * int param_3 = obj.peek();
 * boolean param_4 = obj.empty();
 */
