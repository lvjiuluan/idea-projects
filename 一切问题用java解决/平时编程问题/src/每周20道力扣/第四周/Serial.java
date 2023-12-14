package 每周20道力扣.第四周;

import java.util.*;

public class Serial {
    public static TreeNode getNode(String[] strArr, int i) {
        TreeNode node = null;
        if (i >= strArr.length) return null;
        try {
            node = new TreeNode(Integer.valueOf(strArr[i]));
        } catch (Exception e) {
            // nothing to do
        }
        return node;
    }

    public static TreeNode deserialize(String data) {
        String[] strArr = data.split(",");
        Queue<Integer> indices = new ArrayDeque<>();
        Queue<TreeNode> queue = new ArrayDeque<>();
        int parentIndex = 0;
        TreeNode root = getNode(strArr, parentIndex);
        if (root == null) return null;
        indices.add(parentIndex);
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            parentIndex = indices.poll();
            TreeNode left = getNode(strArr, parentIndex * 2 + 1);
            TreeNode right = getNode(strArr, parentIndex * 2 + 2);
            node.left = left;
            node.right = right;
            if (left != null) {
                indices.add(parentIndex * 2 + 1);
                queue.add(left);
            }
            if (right != null) {
                indices.add(parentIndex * 2 + 2);
                queue.add(right);
            }
        }
        return root;
    }

    public static int getHeight(TreeNode root, int h) {
        if (root == null) return h;
        int leftH = getHeight(root.left, h + 1);
        int rightH = getHeight(root.right, h + 1);
        return leftH > rightH ? leftH : rightH;
    }

    // Encodes a tree to a single string.
    public static String serialize(TreeNode root) {
        if (root == null) return "null";
        Queue<Integer> indices = new ArrayDeque<>();
        Queue<TreeNode> queue = new ArrayDeque<>();
        int h = getHeight(root, 0);
        String[] strArr = new String[(int) Math.pow(2, h) - 1];
        Arrays.fill(strArr, "null");
        int parentIndex = 0;
        queue.add(root);
        indices.add(parentIndex);
        strArr[parentIndex] = String.valueOf(root.val);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            parentIndex = indices.poll();
            if (node.left != null) {
                queue.add(node.left);
                strArr[parentIndex * 2 + 1] = String.valueOf(node.left.val);
                indices.add(parentIndex * 2 + 1);
            }
            if (node.right != null) {
                queue.add(node.right);
                strArr[parentIndex * 2 + 2] = String.valueOf(node.right.val);
                indices.add(parentIndex * 2 + 2);
            }
        }
        return String.join(",", strArr);
    }

    public static void main(String[] args) {
        // 1 2 3 null null 4 5 null null null null
        String[] strArr = {"1", "2", "3", "null", "null", "4", "5"};
        TreeNode root = deserialize(String.join(",", strArr));
        System.out.println(serialize(root));
    }
}

//class TreeNode {
//    int val;
//    TreeNode left;
//    TreeNode right;
//
//    TreeNode(int x) {
//        val = x;
//    }
//}