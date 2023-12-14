<%@ page import="java.sql.Struct" %>
<%@ page import="com.lagou.task05_demo03.Student" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2023-07-06
  Time: 15:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>实现session中数据的钝化和活化操作</title>
</head>
<body>
<%
    Student student = new Student("zhangfei");
    session.setAttribute("student",student);
%>
</body>
</html>
