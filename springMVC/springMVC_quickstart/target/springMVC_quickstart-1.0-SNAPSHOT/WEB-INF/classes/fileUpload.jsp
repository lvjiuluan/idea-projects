<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2023-08-18
  Time: 8:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%--文件上传三要素表单--%>
<form method="post" enctype="multipart/form-data" action="${pageContext.request.contextPath}/fileupload">
    名称: <input type="text" name="username"> <br>
    文件1: <input type="file" name="filePic"> <br>
    文件2: <input type="file" name="filePic"> <br>
    <input type="submit" value="多文件上传">
</form>
</body>
</html>
