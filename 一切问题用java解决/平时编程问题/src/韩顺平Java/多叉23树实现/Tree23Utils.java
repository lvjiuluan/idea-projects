package 韩顺平Java.多叉23树实现;

public class Tree23Utils {
    public static void printTree(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        printTreeHelper(root, "", true, sb);
        System.out.println(sb.toString());
    }

    private static void printTreeHelper(TreeNode node, String prefix, boolean isTail, StringBuilder sb) {
        if (node != null) {
            sb.append(prefix + (isTail ? "\\-- " : "|-- ") + node.values + "\n");
            for (int i = 0; i < node.nodes.size() - 1; i++) {
                printTreeHelper(node.nodes.get(i), prefix + (isTail ? "    " : "|   "), false, sb);
            }
            if (node.nodes.size() >= 1) {
                printTreeHelper(node.nodes.get(node.nodes.size() - 1), prefix + (isTail ? "    " : "|   "), true, sb);
            }
        }
    }

}
