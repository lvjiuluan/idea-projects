package 韩顺平Java.多叉23树实现;


public class Tree23 {
    public TreeNode node;

    public Tree23(int value) {
        node = new TreeNode();
        node.values.add(value);
    }

    /*
     * values包括自己的
     * nodes不包括自己的，只包括子节点
     * */
    public TreeNode find(TreeNode node, int value) {
        if (node.nodeCount() == 2) {
            int target = node.getValue(0);
            if (value < target && node.getTreeNode(0) != null) return find(node.getTreeNode(0), value);
            if (value > target && node.getTreeNode(1) != null) return find(node.getTreeNode(1), value);
            return node;
        } else if (node.nodeCount() == 3) {
            int targetF = node.getValue(0);
            int targetS = node.getValue(1);
            if (value < targetF && node.getTreeNode(0) != null) return find(node.getTreeNode(0), value);
            if (value > targetS && node.getTreeNode(2) != null) return find(node.getTreeNode(2), value);
            if (value > targetF && value < targetS && node.getTreeNode(1) != null)
                return find(node.getTreeNode(1), value);
            return node;
        }
        throw new RuntimeException();
    }

    // 不是找val的值，而是找val应该所属的节点。
    public TreeNode find(int val) {
        return find(node, val);
    }


    public void add(int value) {
        TreeNode oldNode = find(value);
        insert(oldNode, value);
    }

    // 值插入到节点
    public void insert(TreeNode node, int value) {
        node.addValue(value);
        if (node.nodeCount() == 4) {
            // 临时四节点，要拆开
            splitNode(node);
        }
    }

    // 四子节点切分
    public void splitNode(TreeNode node) {
        if (node.parent == null) {
            // 根节点
            // 新根节点
            TreeNode root = new TreeNode();
            root.addValue(node.getValue(1));

            TreeNode left = new TreeNode();
            left.addValue(node.getValue(0));
            left.addTreeNode(node.getTreeNode(0));
            left.addTreeNode(node.getTreeNode(1));

            TreeNode right = new TreeNode();
            right.addValue(node.getValue(2));
            right.addTreeNode(node.getTreeNode(2));
            right.addTreeNode(node.getTreeNode(3));

            root.addTreeNode(left);
            root.addTreeNode(right);

            this.node = root;
        } else {
            // 非根，递归拆分
            // 向上推
            node.parent.addValue(node.removeValue(1));

            // 左右拆开

            TreeNode left = new TreeNode();
            left.addValue(node.getValue(0));
            left.addTreeNode(node.getTreeNode(0));
            left.addTreeNode(node.getTreeNode(1));

            TreeNode right = new TreeNode();
            right.addValue(node.getValue(1));
            right.addTreeNode(node.getTreeNode(2));
            right.addTreeNode(node.getTreeNode(3));

            // 重设左右节点
            node.parent.tail(node, left);
            node.parent.tail(left, node);
            node.parent.removeTreeNode(node);

            if (node.parent.nodeCount() == 4) {
                splitNode(node.parent);
            }
        }
    }
}
