package 每周20道力扣.第二周;

import java.util.Stack;

public class 逆波兰表达式 {
    public static int evalRPN(String[] tokens) {
        Stack<Integer> stack = new Stack<>();
        for (String token : tokens) {
            try {
                int i = Integer.valueOf(token);
                stack.push(i);
            } catch (NumberFormatException e) {
                int j = stack.pop();
                int i = stack.pop();
                if (token.equals("+")) stack.push(i + j);
                if (token.equals("-")) stack.push(i - j);
                if (token.equals("*")) stack.push(i * j);
                if (token.equals("/")) stack.push(i / j);
            }
        }
        return stack.pop();
    }

    public static void main(String[] args) {
        String[] tokens = {"4","13","5","/","+"};
        System.out.println(evalRPN(tokens));
    }
}
