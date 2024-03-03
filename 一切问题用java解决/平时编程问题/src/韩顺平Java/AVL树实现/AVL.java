package 韩顺平Java.AVL树实现;

public class AVL {

    public TreeNode root;


    // 获取树的高度
    public int height(TreeNode root) {
        if (root != null) {
            return root.height;
        }
        return 0;
    }

    // LL
    public TreeNode LL(TreeNode A) {

        // 找到要旋转的节点
        TreeNode B = A.left;
        TreeNode D = B.right;

        // 旋转
        A.left = D;
        B.right = A;

        // 设置高度
        A.height = Math.max(height(A.left), height(A.right)) + 1;
        B.height = Math.max(height(B.left), height(B.right)) + 1;

        return B;
    }

    // RR
    public TreeNode RR(TreeNode A) {
        // 找到要旋转的节点
        TreeNode B = A.right;
        TreeNode D = B.left;

        // 旋转
        A.right = D;
        B.left = A;

        // 设置高度
        A.height = Math.max(height(A.left), height(A.right)) + 1;
        B.height = Math.max(height(B.left), height(B.right)) + 1;

        return B;
    }

    // LR
    public TreeNode LR(TreeNode A) {
        A.left = RR(A.left);
        return LL(A);
    }

    // RL
    public TreeNode RL(TreeNode A) {
        A.right = LL(A.right);
        return RR(A);
    }

    // 插入节点, 递归写法, 递归插入
    public TreeNode insert(TreeNode root, int val) {
        if (root == null) {
            root = new TreeNode(val);
        } else {
            if (val < root.val) {
                root.left = insert(root.left, val);
                // 插入后检查是否平衡
                if ((height(root.left) - height(root.right)) == 2) {
                    if (val < root.left.val) {
                        root = LL(root);
                    } else {
                        root = LR(root);
                    }
                }
            } else {
                root.right = insert(root.right, val);
                // 插入后检查是否平衡
                if ((height(root.right) - height(root.left)) == 2) {
                    if (val < root.right.val) {
                        root = RL(root);
                    } else {
                        root = RR(root);
                    }
                }
            }
        }
        root.height = Math.max(height(root.left), height(root.right)) + 1;
        return root;
    }

    public void insert(int val) {
        root = insert(root, val);
    }
}

class TreeNode {
    public int val;
    public int height; // 维护这个值判断某个节点是否平衡：左右高度是否差值大于1
    public TreeNode left;
    public TreeNode right;

    public TreeNode(int val) {
        this.val = val;
    }


}
/*
 * 左旋 解决 RR问题
 * 右旋 解决 LL问题
 * 左旋 + 右旋 解决 LR问题
 * 右旋 + 左旋 解决 RL问题
 *
 * 一个是旋转方向，一个是问题
 * 旋转的本质这是：A比B大 等价于 B比A小
 * */