package 韩顺平Java.AVL树实现;

import org.junit.Test;

import static org.junit.Assert.*;

public class AVLTest {
    @Test
    public void test01() {
        AVL avl = new AVL();

        avl.insert(3);
        avl.insert(2);
        avl.insert(1);
        avl.insert(4);
        avl.insert(5);
        BTUtils.show(avl.root);
    }
}