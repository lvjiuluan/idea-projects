<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2023-08-16
  Time: 16:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<a href="${pageContext.request.contextPath}/user/simpleParam?id=1&username=杰克">基本类型参数</a>
<form action="${pageContext.request.contextPath}/user/pojoParam" method="post">
    编号 <input name="id" type="text"> <br>
    姓名 <input name="username" type="text"> <br>
    <input type="submit" value="对象类型参数">
</form>
<form action="${pageContext.request.contextPath}/user/arrayParam" method="post">
    编号: <br>
    <input type="checkbox" name="ids" value="1"> <br>
    <input type="checkbox" name="ids" value="2"> <br>
    <input type="checkbox" name="ids" value="3"> <br>
    <input type="checkbox" name="ids" value="4"> <br>
    <input type="submit" value="数组类型参数">
</form>
<form action="${pageContext.request.contextPath}/user/queryParam" method="post">
    搜索关键字: <input type="text" name="keyword"> <br>
    User 对象: <input type="text" name="user.id" placeholder="编号"> <br>
               <input type="text" name="user.username" placeholder="姓名"> <br>
    List 集合: <br>
                第一个元素
                <input type="text" name="userList[0].id" placeholder="第一个元素编号"> <br>
                <input type="text" name="userList[0].username" placeholder="第一个元素姓名"> <br>
                第二个元素
                <input type="text" name="userList[1].id" placeholder="第二个元素编号"> <br>
                <input type="text" name="userList[1].username" placeholder="第二个元素姓名"> <br>
    Map 集合: <br>
                第一个元素
                <input type="text" name="userMap['u1'].id" placeholder="第一个元素编号"> <br>
                <input type="text" name="userMap['u1'].username" placeholder="第一个元素姓名"> <br>
                第二个元素
                <input type="text" name="userMap['u2'].id" placeholder="第二个元素编号"> <br>
                <input type="text" name="userMap['u2'].username" placeholder="第二个元素姓名"> <br>
    <input type="submit" value="复杂类型参数">
</form>

<form action="${pageContext.request.contextPath}/user/pojoParam" method="post">
    编号 <input name="id" type="text"> <br>
    姓名 <input name="username" type="text"> <br>
    <input type="submit" value="对象类型参数">
</form>

<form action="${pageContext.request.contextPath}/user/converterParam" method="post">
    生日: <input type="text" name="birthday"> <br>
    <input type="submit" value="自定义类型转换器">
</form>

<form action="${pageContext.request.contextPath}/user/findByPage" method="post">
    页码: <input type="text" name="pageNo"> <br>
    <input type="submit" value="参数名不一致">
</form>
</body>
</html>
