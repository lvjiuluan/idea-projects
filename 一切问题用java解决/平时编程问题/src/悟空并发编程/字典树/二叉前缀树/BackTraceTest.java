package 悟空并发编程.字典树.二叉前缀树;

public class BackTraceTest {
    public static int found = 0;

    public static void main(String[] args) {
        for (int i = 1; i < 14; i++) {
            found = 0;
            StringBuilder result = new StringBuilder();
            StringBuilder sb = new StringBuilder();
            backtrace(sb, 3, i, result);
            System.out.println(i + ". " + result);
        }
    }

    public static void backtrace(StringBuilder sb, int maxLen, int k, StringBuilder result) {
        if (sb.length() > 0) {
            found++;
            if (found == k) {
                result.append(sb);
                return;
            }
        }
        if (sb.length() == maxLen) {
            return;
        }
        sb.append('a');
        backtrace(sb, maxLen, k, result);
        sb.deleteCharAt(sb.length() - 1);
        sb.append('b');
        backtrace(sb, maxLen, k, result);
        sb.deleteCharAt(sb.length() - 1);
    }
}
