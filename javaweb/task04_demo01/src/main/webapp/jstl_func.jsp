<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2023-07-06
  Time: 7:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<%
  pageContext.setAttribute("var","hello word");
%>
原始字符串为: ${var}
<hr>
判断该字符串是否包含某个指定的字符串结果为: ${fn:contains(var,"hello")}
<hr>
将字符串中所有字符转换为大写为: ${fn:toUpperCase(var)}
<hr>
将字符串中所有字符转换为小写为: ${fn:toLowerCase(var)}
<body>

</body>
</html>
