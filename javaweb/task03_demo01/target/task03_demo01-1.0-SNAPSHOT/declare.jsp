<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2023-07-02
  Time: 10:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>测试变量的声明和打印</title>
</head>
<%!
    int ia; // 声明一个全局变量
    public void show(){
        System.out.println("这是一个全局方法");
    }
    public class myClass{
        {
            // 构造快
            System.out.println("这是一个全局类哦");
        }
    }
%>
<%
    int ib = 20; // 这是一个局部变量
    for (int i = 0; i < 3; i++) {
        System.out.println("随便放入java se代码吧！");
    }
%>
<body>
<%=ia+1%>
<%=ib%>
<%="我就暂时写到这里"%>
</body>
</html>
