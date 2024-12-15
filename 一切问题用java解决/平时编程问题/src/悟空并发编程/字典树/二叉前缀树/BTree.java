package 悟空并发编程.字典树.二叉前缀树;

import 韩顺平Java.JVM引用.B;

public class BTree {

    private BTreeNode root;
    private int kNode;
    private int maxDepth;

    public static void main(String[] args) {
        BTree bTree = new BTree();
        int n = 3; // 最大字符串长度
        bTree.maxDepth = n;
        bTree.buildTree(n);
        for (int i = 1; i <= 14; i++) { // 总共有 2^1 + 2^2 + 2^3 = 14 个字符串
            StringBuffer sb = new StringBuffer();
            System.out.println("第 " + i + " 个字符串: " + bTree.findKthString(bTree.root, i, sb));
        }
    }

    /**
     * 构建二叉树，树的深度为 maxDepth
     */
    public void buildTree(int depth) {
        root = new BTreeNode();
        buildTreeHelper(root, depth);
    }

    /**
     * 递归地构建二叉树
     */
    private void buildTreeHelper(BTreeNode node, int depth) {
        if (depth == 0) {
            return;
        }
        node.left = new BTreeNode();  // 左子节点代表字符 'a'
        node.right = new BTreeNode(); // 右子节点代表字符 'b'
        buildTreeHelper(node.left, depth - 1);
        buildTreeHelper(node.right, depth - 1);
    }

    /**
     * 查找字典序中第 k 个字符串
     */
    public String findKthString(BTreeNode node, int k, StringBuffer sb) {
        this.kNode = k;
        StringBuilder result = new StringBuilder();
        findKthStringHelper(node, sb, result, 0);
        return result.toString();
    }

    /**
     * 辅助回溯方法
     * @param node 当前节点
     * @param sb 当前构建的字符串
     * @param result 结果存储
     * @param depth 当前深度
     * @return 是否已找到第 k 个字符串
     */
    private boolean findKthStringHelper(BTreeNode node, StringBuffer sb, StringBuilder result, int depth) {
        if (node == null || depth > maxDepth) {
            return false;
        }

        // 如果当前字符串非空，则考虑将其计入
        if (sb.length() > 0) {
            kNode--;
            if (kNode == 0) {
                result.append(sb.toString());
                return true;
            }
        }

        // 如果当前深度小于最大深度，则继续添加字符
        if (depth < maxDepth) {
            // 选择字符 'a'
            sb.append('a');
            if (findKthStringHelper(node.left, sb, result, depth + 1)) {
                return true;
            }
            sb.deleteCharAt(sb.length() - 1); // 回溯

            // 选择字符 'b'
            sb.append('b');
            if (findKthStringHelper(node.right, sb, result, depth + 1)) {
                return true;
            }
            sb.deleteCharAt(sb.length() - 1); // 回溯
        }

        return false;
    }
}

class BTreeNode {
    public BTreeNode left;
    public BTreeNode right;
}

