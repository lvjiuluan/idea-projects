package 韩顺平Java.多叉23树实现;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class Tree23Test {
    @Test
    public void test01() {
        Tree23 tree23 = new Tree23(10);
        tree23.add(8);
        tree23.add(12);
        Tree23Utils.printTree(tree23.node);
    }

    @Test
    public void test02() {
        Tree23 tree23 = new Tree23(10);
        tree23.add(8);
        tree23.add(12);
        tree23.add(6);
        tree23.add(7);
        tree23.add(2);
        tree23.add(4);
        System.out.println(tree23.node);
    }

}