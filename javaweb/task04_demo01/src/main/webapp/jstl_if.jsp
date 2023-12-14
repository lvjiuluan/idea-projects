<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2023-07-06
  Time: 7:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<c:set var="age" value="20" scope="page"></c:set>
<c:out value="${age}"></c:out>
<hr>
<c:if test="${age >= 18}">
    <c:out value="已经成年了"></c:out>
</c:if>

<body>

</body>
</html>
