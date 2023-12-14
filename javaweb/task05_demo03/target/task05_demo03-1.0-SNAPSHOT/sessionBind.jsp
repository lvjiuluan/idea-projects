<%@ page import="com.lagou.task05_demo03.Person" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2023-07-06
  Time: 15:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>实现Session中对象的绑定与删除</title>
</head>
<%
    Person person = new Person("张飞", 20);
    session.setAttribute("pserson",person);
    session.removeAttribute("person");
%>
<body>

</body>
</html>
