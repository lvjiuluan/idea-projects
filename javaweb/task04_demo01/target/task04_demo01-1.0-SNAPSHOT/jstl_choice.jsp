<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2023-07-06
  Time: 7:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<c:set var="score" value="60"></c:set>
<c:out value="${score}"></c:out>
<c:choose>
    <c:when test="${score > 60}">
        <c:out value="不错"></c:out>
    </c:when>
    <c:when test="${score == 60}">
        <c:out value="60分万岁"></c:out>
    </c:when>
    <c:otherwise>
        <c:out value="继续加油"></c:out>
    </c:otherwise>
</c:choose>
<body>

</body>
</html>
