package 韩顺平Java.二叉搜索树;

public class BST {
    public TreeNode root;

    public BST() {
        this.root = null;
    }

    public int height(TreeNode root) {
        if (root == null || root.left == null || root.right == null) return 0;
        int l = height(root.left);
        int r = height(root.right);
        return l > r ? l + 1 : r + 1;
    }

    public TreeNode search(int val) {
        TreeNode p = root;
        while (p != null) {
            if (p.val == val) return p;
            if (val > p.val) {
                p = p.right;
            } else {
                p = p.left;
            }
        }
        return null;
    }

    public void insert(int val) {
        if (root == null) {
            root = new TreeNode(val);
            return;
        }
        TreeNode p = root;
        TreeNode q = root;
        boolean flag = true;
        while (p != null) {
            q = p;
            if (val >= p.val) {
                p = p.right;
                flag = true;
            } else {
                p = p.left;
                flag = false;
            }
        }
        if (flag) {
            q.right = new TreeNode(val);
        } else {
            q.left = new TreeNode(val);
        }

    }

}

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {
    }

    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return "TreeNode{" +
                "val=" + val +
                ", left=" + left +
                ", right=" + right +
                '}';
    }
}