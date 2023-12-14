package com.lagou.xml04;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import java.util.List;

public class TestXPath {
    @Test
    public void test1() throws DocumentException {
        // 使用selectSingleNode方法查询指定结点信息
        // 1 创建XML解析对象
        SAXReader saxReader = new SAXReader();
        // 2 创建文档对象
        Document document = saxReader.read("D:\\IDEA Projects\\jdbc_work\\xml_task03\\src\\com\\lagou\\xml04\\book.xml");
        // 3 调用selectSingleNode方法
        Node node1 = document.selectSingleNode("/bookstore/book/name");
        System.out.println(node1.getName()+","+node1.getText());
        Node node2 = document.selectSingleNode("/bookstore/book[2]/name");
        System.out.println(node2.getName()+","+node2.getText());
    }
    @Test
    public void test2() throws DocumentException {
        // 使用selectSingleNode方法获取属性值，或者通过属性值获取节点信息
        // 使用selectSingleNode方法查询指定结点信息
        // 1 创建XML解析对象
        SAXReader saxReader = new SAXReader();
        // 2 创建文档对象
        Document document = saxReader.read("D:\\IDEA Projects\\jdbc_work\\xml_task03\\src\\com\\lagou\\xml04\\book.xml");
        // 获取第一个book节点中的id属性的值
        Node node = document.selectSingleNode("/bookstore/book/attribute::id");
        System.out.println(node.getName()+", "+node.getText());
        // 获取最后book节点中的id属性的值
        Node nodeLast = document.selectSingleNode("/bookstore/book[last()]/attribute::id");
        System.out.println(nodeLast.getName()+", "+nodeLast.getText());
        // 通过属性值获取节点
        Node name = document.selectSingleNode("/bookstore/book[@id='book2']/name");
        System.out.println(name.getText());

        Node name1 = name.selectSingleNode("/bookstore/book[@id='book2']/name");
        System.out.println(name1.getText());
    }

    /***
     * 获取所有指定名称的节点，获取多个节点
     */
    @Test
    public void test3() throws DocumentException {
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read("D:\\IDEA Projects\\jdbc_work\\xml_task03\\src\\com\\lagou\\xml04\\book.xml");
        // 1 查询所有的节点
        List<Node> nodes = document.selectNodes("//*");
        for (Node node : nodes) {
            System.out.println(node.getName());
        }
        List<Node> names = document.selectNodes("//name");
        for (Node name : names) {
            System.out.println(name.getText());
        }
        // 获取id值为book1的节点中的所有字节的内容
        // '//' 表示多个  '/' 表示一个
        List<Node> list = document.selectNodes("//bookstore/book[@id='book1']//*");
        for (Node node : list) {
            System.out.println(node.getText());
        }

    }
}
