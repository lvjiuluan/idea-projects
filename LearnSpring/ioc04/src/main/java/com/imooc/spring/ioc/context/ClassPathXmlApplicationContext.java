package com.imooc.spring.ioc.context;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ClassPathXmlApplicationContext implements ApplicationContext {

    private Map iocContainer = new HashMap<>();

    public ClassPathXmlApplicationContext() {
        try {
            String filePath = this.getClass().getResource("applicationContext.xml").getPath();
            // 防止路径中有中文报错
            filePath = new URLDecoder().decode(filePath, "UTF-8");
            // xml解析组件
            SAXReader reader = new SAXReader();
            // XML文档对象
            Document document = reader.read(new File(filePath));
            // 读取内容
            // 读取根节点标签下所有的bean标签
            List<Node> beans = document.getRootElement().selectNodes("bean");
            for (Node bean : beans) {
                String id = ((Element) bean).attributeValue("id");
                String className = ((Element) bean).attributeValue("class");
                // 使用反射技术, forName加载指定类对象
                // 获取className类的类对象
                Class c = Class.forName(className);
                // 通过默认构造方法创建一个className类的实例
                Object obj = c.newInstance();
                iocContainer.put(id, obj);

                /*
                 *  对象属性注入
                 * */
                List<Node> properties = ((Element) bean).selectNodes("property");
                for (Node property : properties) {
                    String name = ((Element) property).attributeValue("name");
                    String value = ((Element) property).attributeValue("value");
                    String setMethodName = "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
                    System.out.println("准备执行setter method name 注入数据" + setMethodName);
                    Method method = c.getMethod(setMethodName, String.class);
                    method.invoke(obj, value); // 实现了通过setter方法注入属性的功能
                }

            }
            System.out.println("IoC容器初始化完毕");
        } catch (Exception e) {
        }
    }

    @Override
    public Object getBean(String beanId) {
        Object obj = iocContainer.get(beanId);
        return obj;
    }
}
