package com.lagou.xml03;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.util.List;

/***
 * 获取XML文件中所有元素
 */
public class TestDom4j {
    public static void main(String[] args) throws DocumentException {
        // 1 获取XML解析对象
        SAXReader reader = new SAXReader();
        // 2 解析XML，获取文档对象
        Document document = reader.read("D:\\IDEA Projects\\jdbc_work\\xml_task03\\src\\com\\lagou\\xml03\\user.xml");
        // 3 获取根元素对象
        Element rootElement = document.getRootElement();
        System.out.println("根元素名称："+rootElement.getName());
        // 4 获取所有的子元素
        List<Element> elements = rootElement.elements();
        for (Element element : elements) {
            System.out.println(element.getName());
            List<Element> elements1 = element.elements();
            for (Element o : elements1) {
                System.out.println("user标签下的字节点"+o.getName()+"值为："+o.getText());
            }
        }
        // 获取元素的第一个子节点
        Element user1 = elements.get(0); // list 的方法
        String id = user1.attributeValue("id");
        String name = user1.elementText("name");
        String hobby = user1.element("hobby").getText();
        System.out.println(id+name+hobby);
    }
}
