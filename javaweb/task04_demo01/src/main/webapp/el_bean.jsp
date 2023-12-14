<%@ page import="com.lagou.task04_demo01.Person" %>
<%@ page import="java.lang.reflect.Field" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2023-07-05
  Time: 18:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>实现Bean对象的属性的获取</title>
</head>
<body>
<%--使用jap原始方式--%>
<%
    Person person = new Person();
    person.setName("zhangfei");
    person.setAge(30);
    pageContext.setAttribute("person",person);
    Class<?> clazz = request.getClass();
    Field[] declaredFields = clazz.getDeclaredFields();
    for (Field declaredField : declaredFields) {
        declaredField.setAccessible(true);
        System.out.println(declaredField.getName());
    }

%>
<%--<%= "获取到的姓名为:" + person.getName()%> <br/>
<%= "获取到的年龄为:" + person.getAge()%>--%>
<%--使用EL表达式实现属性的获取与打印--%>
获取到的姓名为: ${person.name} <br/>
获取到的年龄为: ${person.age} <br/>
获取到的对象为: ${person} <br/>
</body>
</html>
