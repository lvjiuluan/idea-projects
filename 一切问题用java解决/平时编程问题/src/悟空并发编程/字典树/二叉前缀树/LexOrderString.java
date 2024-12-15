package 悟空并发编程.字典树.二叉前缀树;

public class LexOrderString {
    // 计数器，用于记录已遍历的字符串数量
    private int count = 0;
    // 目标位置 k
    private int k;
    // 结果字符串
    private String result = null;
    // 标记是否已找到结果
    private boolean found = false;

    // 前缀树节点定义
    class TrieNode {
        TrieNode[] children = new TrieNode[2]; // 0: 'a', 1: 'b'
    }

    // 构建前缀树，生成所有可能的字符串
    public void buildTrie(TrieNode node, int depth, int maxDepth) {
        if (depth >= maxDepth) {
            return;
        }
        // 创建 'a' 子节点
        node.children[0] = new TrieNode();
        buildTrie(node.children[0], depth + 1, maxDepth);
        // 创建 'b' 子节点
        node.children[1] = new TrieNode();
        buildTrie(node.children[1], depth + 1, maxDepth);
    }

    // 按字典序遍历前缀树，寻找第 k 个字符串
    public void traverse(TrieNode node, StringBuilder sb, int maxDepth) {
        if (found) {
            return;
        }
        if (sb.length() > 0) {
            count++;
            if (count == k) {
                result = sb.toString();
                found = true;
                return;
            }
        }
        if (sb.length() >= maxDepth) {
            return;
        }
        // 按字母顺序访问子节点，'a' 在前，'b' 在后
        for (int i = 0; i < 2; i++) {
            if (node.children[i] != null) {
                sb.append((char) ('a' + i));
                traverse(node.children[i], sb, maxDepth);
                sb.deleteCharAt(sb.length() - 1); // 回溯
            }
        }
    }

    // 主函数，输入 n 和 k，输出第 k 个字符串
    public String findKthString(int n, int k) {
        this.k = k;
        found = false;
        TrieNode root = new TrieNode();
        buildTrie(root, 0, n);
        StringBuilder sb = new StringBuilder();
        traverse(root, sb, n);
        return result;
    }

    // 测试函数
    public static void main(String[] args) {
        LexOrderString los = new LexOrderString();
        int n = 3; // 最大长度
        for (int i = 1; i < 14; i++) {
            System.out.println("第 " + i + " 个字符串是：" + los.findKthString(n,i));
        }
    }
}

