package com.lagou.utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeanFactory {
    private static Map<String, Object> iocMap = new HashMap<>();

    static {
        // 1 读取配置文件
        InputStream resourceAsStream = BeanFactory.class.getClassLoader().getResourceAsStream("beans.xml");
        // 2 解析xml
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(resourceAsStream);
            // 3 编写xpath表达式
            String xpath = "//bean";
            List<Element> list = document.selectNodes(xpath);
            for (Element element : list) {
                // 4 使用反射创建对象实例，存到map集合中
                String id = element.attributeValue("id");
                String className = element.attributeValue("class");
                Object o = Class.forName(className).newInstance();
                iocMap.put(id, o);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private BeanFactory() {
    }

    public static Object getBean(String beanId) {
        return iocMap.get(beanId);
    }
}
