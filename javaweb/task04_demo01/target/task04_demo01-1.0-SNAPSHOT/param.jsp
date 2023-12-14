<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2023-07-05
  Time: 17:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>实现一个简单页面，负责向jsp传递http请求</title>
</head>
<form action="el_param.jsp" method="post">
    姓名: <input type="text" name="name"><br/>
    爱好: <input type="checkbox" name="hobby" value="唱歌">唱歌<br/>
          <input type="checkbox" name="hobby" value="跳舞">跳舞<br/>
          <input type="checkbox" name="hobby" value="打篮球">打篮球<br/>
          <input type="checkbox" name="hobby" value="学习">学习<br/>
    <input type="submit" value="提交">
</form>
<body>

</body>
</html>
