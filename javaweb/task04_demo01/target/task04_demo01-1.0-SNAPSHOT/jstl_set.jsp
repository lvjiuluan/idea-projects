<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2023-07-06
  Time: 7:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<c:set var="name" value="zhangfei" scope="page"></c:set>
<c:out value="${name}"></c:out>
<jsp:useBean id="person" class="com.lagou.task04_demo01.Person" scope="page"></jsp:useBean>
<c:set property="name" value="guanyu" target="${person}" ></c:set>
<c:set property="age" value="35" target="${person}" ></c:set>
<c:out value="${person.name}"></c:out>
<c:out value="${person.age}"></c:out>
</body>
</html>
