package 悟空并发编程.字典树;

public class BackTraceTest02 {
    static int found = 0;
    public static void main(String[] args) {
        for (int i = 1; i <= 14; i++) {
            found = 0;
            StringBuilder sb = new StringBuilder();
            StringBuilder result = new StringBuilder();
            backtrace(sb, 3, i, result);
            System.out.println(i + ". " + result);
        }
    }

    public static void backtrace(StringBuilder sb, int n, int k, StringBuilder result) {
        if (sb.length() > 0) {
            found++;
            if (found == k) {
                result.append(sb);
                return;
            }
        }
        if (sb.length() == n) {
            return;
        }
        sb.append('a');
        backtrace(sb, n, k, result);
        sb.deleteCharAt(sb.length() - 1);
        sb.append('b');
        backtrace(sb, n, k, result);
        sb.deleteCharAt(sb.length() - 1);
    }
}
