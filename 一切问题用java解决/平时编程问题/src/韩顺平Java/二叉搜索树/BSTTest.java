package 韩顺平Java.二叉搜索树;


import org.junit.Test;

public class BSTTest {

    @Test
    public void height() {
        BST bst = new BST();
        bst.insert(2);
        bst.insert(1);
        bst.insert(3);
        System.out.println(bst.height(bst.root));
    }
}