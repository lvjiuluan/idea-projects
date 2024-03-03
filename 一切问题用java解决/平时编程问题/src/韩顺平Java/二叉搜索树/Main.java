package 韩顺平Java.二叉搜索树;

import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        BST bst = new BST();
        Random random = new Random();
        Set<Integer> integers = random.ints(0, 1000).
                limit(100).boxed().collect(Collectors.toSet());
        int searchVal = 0;
        for (Integer integer : integers) {
            bst.insert(integer);
            searchVal = integer;
        }
        // 验证
        Solution solution = new Solution();
        System.out.println(solution.isValidBST(bst.root));
        // 搜索
        TreeNode treeNode = bst.search(bst.root.val);
        System.out.println(treeNode);
    }
}
