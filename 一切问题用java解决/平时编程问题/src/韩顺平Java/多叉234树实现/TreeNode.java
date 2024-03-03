package 韩顺平Java.多叉234树实现;

import java.util.LinkedList;
import java.util.List;

public class TreeNode {
    public TreeNode parent;
    public List<Integer> values;
    public List<TreeNode> nodes;

    public TreeNode() {
        values = new LinkedList<>();
        nodes = new LinkedList<>();
    }

    /*
     * 2 节点 1个数据
     * 3 节点 2个数据
     * 4节点 3个数据
     * 临时5节点 4个数据
     * */
    public int nodeCount() {
        return values.size() + 1;
    }

    // 对values操作
    // 向一个节点中添加值
    // [4, 6]  => + 5 => [4, 5, 6]
    public void addValue(int value) {
        for (int i = 0; i < values.size(); i++) {
            if (value < values.get(i)) {
                values.add(i, value);
                return;
            }
        }
        values.add(value);
    }

    public int getValue(int i) {
        return values.get(i);
    }

    public int removeValue(int i) {
        return values.remove(i);
    }

    // 对节点操作
    // 添加节点
    public void addTreeNode(TreeNode node) {
        if (node == null) return;
        node.parent = this;
        nodes.add(node);
    }

    public TreeNode getTreeNode(int i) {
        if (i < nodes.size()) return nodes.get(i);
        return null;
    }

    public void removeTreeNode(TreeNode node) {
        nodes.remove(node);
    }

    // 插入nodes中prev节点之后
    public void tail(TreeNode prev, TreeNode node) {
        node.parent = this;
        int i = nodes.indexOf(prev);
        nodes.add(i + 1, node);
    }

    @Override
    public String toString() {
        return "Node{" +
                "values=" + values +
                '}';
    }
}
