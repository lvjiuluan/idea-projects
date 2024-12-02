package 悟空并发编程.字典树.二叉前缀树;

public class KthLexStringBinaryTree {
    private int count = 0;
    private String result = "";

    public String kthString(int n, int k) {
        dfs(n, k, new StringBuilder());
        return result;
    }

    private void dfs(int n, int k, StringBuilder sb) {
        if (sb.length() == n) {
            count++;
            if (count == k) {
                result = sb.toString();
            }
            return;
        }
        // 先添加'a'
        sb.append('a');
        dfs(n, k, sb);
        if (count >= k) return; // 提前剪枝
        sb.deleteCharAt(sb.length() - 1);
        // 再添加'b'
        sb.append('b');
        dfs(n, k, sb);
        if (count >= k) return; // 提前剪枝
        sb.deleteCharAt(sb.length() - 1);
    }

    public static void main(String[] args) {
        KthLexStringBinaryTree solver = new KthLexStringBinaryTree();
        int n = 3; // 字符串长度
        int k = 4; // 第k个字符串
        String kthString = solver.kthString(n, k);
        System.out.println("第 " + k + " 个字符串是: " + kthString);
    }
}

