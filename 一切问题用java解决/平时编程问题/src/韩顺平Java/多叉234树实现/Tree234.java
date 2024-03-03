package 韩顺平Java.多叉234树实现;

public class Tree234 {
    public TreeNode node;

    public Tree234(int val) {
        node = new TreeNode();
        node.addValue(val);
    }

    /*
     * 能往下层节点走就往下层节点走
     * 不要集中放在根节点
     *
     * */
    // 查询val所属节点
    public TreeNode find(int val) {
        return find(node, val);
    }

    public TreeNode find(TreeNode node, int val) {
        if (node.nodeCount() == 2) {
            int target = node.getValue(0);
            if (val < target && node.getTreeNode(0) != null) return find(node.getTreeNode(0), val);
            if (val > target && node.getTreeNode(1) != null) return find(node.getTreeNode(1), val);
            return node;
        }
        if (node.nodeCount() == 3) {
            int targetF = node.getValue(0);
            int targetS = node.getValue(1);
            if (val < targetF && node.getTreeNode(0) != null) return find(node.getTreeNode(0), val);
            if (val > targetS && node.getTreeNode(2) != null) return find(node.getTreeNode(2), val);
            if (val > targetF && val < targetS && node.getTreeNode(1) != null)
                find(node.getTreeNode(1), val);
            return node;
        }
        if (node.nodeCount() == 4) {
            int targetF = node.getValue(0);
            int targetS = node.getValue(1);
            int targetG = node.getValue(2);
            if (val < targetF && node.getTreeNode(0) != null) return find(node.getTreeNode(0), val);
            if (val > targetG && node.getTreeNode(3) != null) return find(node.getTreeNode(3), val);
            if (val > targetF && val < targetS && node.getTreeNode(1) != null)
                find(node.getTreeNode(1), val);
            if (val > targetS && val < targetG && node.getTreeNode(2) != null)
                find(node.getTreeNode(2), val);
            return node;
        }
        throw new RuntimeException();
    }

    // 插入值
    public void add(int val) {
        TreeNode toBeSelectedNode = find(val);
        insert(toBeSelectedNode, val);
    }

    public void insert(TreeNode node, int val) {
        node.addValue(val);
        if (node.nodeCount() == 5) {
            // 临时5子节点，切分
            splitTreeNode(node);
        }
    }

    public void splitTreeNode(TreeNode node) {
        if (node.parent == null) {
            // 根节点的情况
            // 创建一个新的根节点
            TreeNode root = new TreeNode();
            root.addValue(node.getValue(1));

            // 创建新的左节点
            TreeNode left = new TreeNode();
            left.addValue(node.getValue(0));
            left.addTreeNode(node.getTreeNode(0));
            left.addTreeNode(node.getTreeNode(1));

            // 创建新的右节点
            TreeNode right = new TreeNode();
            right.addValue(node.getValue(2));
            right.addValue(node.getValue(3));
            right.addTreeNode(node.getTreeNode(2));
            right.addTreeNode(node.getTreeNode(3));
            right.addTreeNode(node.getTreeNode(4));

            // 设置左右节点
            root.addTreeNode(left);
            root.addTreeNode(right);
            // 修改根节点
            this.node = root;
        } else {
            // 非根情况
            // 将node的值上推
            node.parent.addValue(node.removeValue(1));

            // 创建新的左节点
            TreeNode left = new TreeNode();
            left.addValue(node.getValue(0));
            left.addTreeNode(node.getTreeNode(0));
            left.addTreeNode(node.getTreeNode(1));

            // 创建新的右节点
            TreeNode right = new TreeNode();
            right.addValue(node.getValue(1));
            right.addValue(node.getValue(2));
            right.addTreeNode(node.getTreeNode(2));
            right.addTreeNode(node.getTreeNode(3));
            right.addTreeNode(node.getTreeNode(4));

            // 重设左右节点
            node.parent.tail(node, left);
            node.parent.tail(left, right);
            node.removeTreeNode(node);

            if (node.parent.nodeCount() == 5) {
                splitTreeNode(node.parent);
            }
        }
    }
}
