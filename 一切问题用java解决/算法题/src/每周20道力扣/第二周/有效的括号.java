package 每周20道力扣.第二周;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class 有效的括号 {
    public static boolean isValid(String s) {
        Stack<String> stack = new Stack<>();
        Map<String, String> map = new HashMap<>();
        map.put("(", ")");
        map.put("[", "]");
        map.put("{", "}");
        for (int i = 0; i < s.length(); i++) {
            String c = s.substring(i, i + 1);
            if (c.equals("(") || c.equals("[") || c.equals("{")) {
                stack.push(c);
            } else {
                if (stack.isEmpty()) return false;
                String top = stack.peek();
                String cc = map.get(top);
                if (cc.equals(c)) stack.pop();
                else stack.push(c);
            }
        }
        return stack.isEmpty();
    }


    public static void main(String[] args) {
        System.out.println(isValid("()"));
    }
}
