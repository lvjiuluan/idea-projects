package pre.Object_;

import java.util.Stack;

public class BinaryTree {

    public static void preOrder(TreeNode root){
        if (root == null){
            return;
        }

        System.out.println(root.val);
        // 遍历左子树
        preOrder(root.left);
        // 遍历右子树
        preOrder(root.right);
    }

    public static void preOrderStack(TreeNode root){
        if (root == null){
            return;
        }

        Stack<TreeNode> stack = new Stack<>();

        stack.push(root);

        while (!stack.isEmpty()){
            TreeNode node = stack.pop();
            System.out.println(node.val);

            // 先压入右子树，再压入左子树
            if (root.right != null){
                stack.push(node.right);
            }
            if (root.left != null){
                stack.push(node.left);
            }
        }
    }

}

class TreeNode{
    int val;
    TreeNode left;
    TreeNode right;
}

