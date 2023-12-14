package 每周20道力扣.第四周;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class 最近公共祖先 {
    static LinkedList<TreeNode> path = new LinkedList<>();
    static List<TreeNode> list = new ArrayList<>();
    static LinkedList<TreeNode> parents;

    public static void dfs(TreeNode root, TreeNode p) {
        if (root == p) {
            parents = new LinkedList<>(path);
        }
        ;
        if (root.left != null) {
            path.addFirst(root.left);
            dfs(root.left, p);
            path.removeFirst();
        }
        if (root.right != null) {
            path.addFirst(root.right);
            dfs(root.right, p);
            path.removeFirst();
        }
    }

    public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        path.push(root);
        dfs(root, p);
        for (TreeNode parent : parents) {
            if (isParent(parent, q)) {
                return parent;
            }
        }
        return null;
    }

    private static boolean isParent(TreeNode parent, TreeNode q) {
        if (parent == null) return false;
        if (parent == q) return true;
        return isParent(parent.left, q) || isParent(parent.right, q);
    }

    public static void preOrder(TreeNode root) {
        if (root == null) return;
        list.add(root);
        preOrder(root.left);
        preOrder(root.right);
    }

    public static void main(String[] args) {
        String[] strArr = {"5", "4", "8", "11", "null", "13", "4", "7", "2", "null", "null", "null", "null", "5", "1"};
        TreeNode root = Serial.deserialize(String.join(",", strArr));
        preOrder(root);
        TreeNode p = list.get(2);
        TreeNode q = list.get(3);
        System.out.println(p.val + "," + q.val);
        path.addFirst(root);
        System.out.println(lowestCommonAncestor(root,p,q).val);

    }
}
