<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2023-07-07
  Time: 16:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
${pageContext.request.contextPath}<hr>
<a href="${pageContext.request.contextPath}/test?methodName=addCourse">新建课程</a><br>
<a href="${pageContext.request.contextPath}/test?methodName=findByName">根据课程名查询</a><br>
<a href="${pageContext.request.contextPath}/test?methodName=findByStatus">根据课程状态查询</a><br>
<a href="${pageContext.request.contextPath}/test2?methodName=show">show方法</a><br>

</body>


</html>
