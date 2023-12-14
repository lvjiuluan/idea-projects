<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2023-07-06
  Time: 7:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<%
  String[] arr = {"11","22","33","44","55"};
  pageContext.setAttribute("arr",arr);
%>

<body>
<c:forEach var="ts" items="${arr}">
    <c:out value="${ts}"></c:out>
</c:forEach>
<hr>
<c:forEach var="ts" items="${arr}" step="2">
    <c:out value="${ts}"></c:out>
</c:forEach>
<hr>
<c:forEach var="ts" items="${arr}" begin="1" end="3">
    <c:out value="${ts}"></c:out>
</c:forEach>

</body>
</html>
