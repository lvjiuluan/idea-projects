package 每周20道力扣.第四周;


import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;

public class Route {
    int targetSum;
    LinkedList<LinkedList<Integer>> paths = new LinkedList<>();

    public int getSum(LinkedList<Integer> path) {
        int sum = 0;
        for (Integer p : path) {
            p += sum;
        }
        return sum;
    }

    public void dfs(TreeNode root, LinkedList<Integer> path) {
        if (root.left == null && root.right == null) {
            int sum = getSum(path);
            if (sum == targetSum) paths.add(path);
            return;
        }
        if (root.left != null) {
            path.addLast(root.left.val);
            dfs(root.left, path);
            path.removeLast();
        }
        if (root.right != null) {
            path.addLast(root.right.val);
            dfs(root.right, path);
            path.removeLast();
        }
    }

    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        LinkedList<Integer> path = new LinkedList<>();
        this.targetSum = targetSum;
        path.add(root.val);
        dfs(root, path);
        List<List<Integer>> lists = new ArrayList<>(paths);
        return lists;
    }

    public static void main(String[] args) {
        String[] strArr = {"5","4","8","11","null","13","4","7","2","null","null","null","null","5","1"};
        TreeNode root = Serial.deserialize(String.join(",", strArr));
        Route route = new Route();
        route.pathSum(root,22);
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