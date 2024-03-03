package 韩顺平Java.多叉234树实现;

import org.junit.Test;
import 韩顺平Java.多叉23树实现.Tree23;

import static org.junit.Assert.*;

public class Tree234Test {
    @Test
    public void test01() {
        Tree234 tree234 = new Tree234(10);
        tree234.add(8);
        tree234.add(12);
        tree234.add(6);
        tree234.add(7);
        tree234.add(2);
        tree234.add(4);
        System.out.println(tree234);
    }
}