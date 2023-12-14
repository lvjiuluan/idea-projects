package 每周20道力扣.第一周;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class 反转栈 {
    public static Stack<Integer> f(Stack<Integer> stack, int i) {
        if (stack.isEmpty()) {
            stack.push(i);
            return stack;
        }
        int top = stack.pop();
        stack = f(stack, i);
        stack.push(top);
        return stack;
    }

    public static Stack<Integer> reverse(Stack<Integer> stack) { // 1 2 3 4 5
        if (stack.isEmpty() || stack.size() == 1) return stack;
        int top = stack.pop(); // 1 2 3 4
        stack = reverse(stack); // 4 3 2 1
        stack = f(stack, top); // 5 4 3 2 1
        return stack;
    }

    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);
        System.out.println(stack);
        stack = reverse(stack);
        System.out.println(stack);
        Map<Integer,Integer> map = new HashMap<>();
        map.get(1);
    }
}
