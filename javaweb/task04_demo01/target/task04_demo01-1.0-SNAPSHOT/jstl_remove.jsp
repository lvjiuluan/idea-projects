<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2023-07-06
  Time: 7:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<c:set var="name" value="liubei"></c:set>
<c:out value="${name}"></c:out>
<c:remove var="name"></c:remove>
<c:out value="${name}" default="无名"></c:out>
<body>

</body>
</html>
