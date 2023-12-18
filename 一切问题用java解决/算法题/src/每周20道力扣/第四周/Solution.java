package 每周20道力扣.第四周;


class Solution {
    int k = 0;

    public TreeNode build(int start, int end, int[] preorder, int[] inorder) {
        Integer rootVal = null;
        Integer rootIndex = null;
        for (int i = start; i < end; i++) {
            if (k < inorder.length && inorder[i] == preorder[k]) {
                rootVal = inorder[i];
                rootIndex = i;
                k += 1;
            }
        }
        if (rootVal != null) {
            TreeNode root = new TreeNode(rootVal);
            root.left = build(start, rootIndex, preorder, inorder);
            root.right = build(rootIndex + 1, end, preorder, inorder);
            return root;
        }
        return null;
    }

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        return build(0, inorder.length, preorder, inorder);
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        int[] preorder = {1,2};
        int[] inorder = {1,2};
        solution.buildTree(preorder,inorder);
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
}