<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2023-07-05
  Time: 12:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>实现表格的绘制</title>
</head>
<body>
<table>
    <tr>
        <td>id</td>
        <td>name</td>
        <td>age</td>
        <td>salary</td>
    </tr>
    <%
        for (int i = 1; i <= 5; i++) {
    %>
    <tr>
        <td> <%=i%> </td>
        <td> <%=i%> </td>
        <td> <%=i%> </td>
        <td> <%=i%> </td>
        <td> <%=i%> </td>
    </tr>
    <%
        }
    %>
</table>
</body>
</html>
