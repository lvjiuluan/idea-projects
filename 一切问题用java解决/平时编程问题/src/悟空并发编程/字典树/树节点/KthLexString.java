package 悟空并发编程.字典树.树节点;

public class KthLexString {
    public static int k;
    public static boolean found;
    public static String result;


    public static void main(String[] args) {
        int n = 3;
        for (int i = 1; i <= 14; i++) {
            k = i;
            found = false;
            StringBuilder sb = new StringBuilder();
            backtrace(sb, n);
            System.out.println(i + ". " + result);
        }
    }

    public static void backtrace(StringBuilder sb, int depth) {
        if (found) {
            return;
        }
        if (sb.length() > 0) {
            k--;
            if (k == 0) {
                result = sb.toString();
                found = true;
                return;
            }
        }
        if (sb.length() == depth) {
            return;
        }
        sb.append('a');
        backtrace(sb, depth);
        sb.deleteCharAt(sb.length() - 1);
        sb.append('b');
        backtrace(sb, depth);
        sb.deleteCharAt(sb.length() - 1);
    }
}
