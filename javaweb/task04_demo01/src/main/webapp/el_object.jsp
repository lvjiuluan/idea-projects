<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2023-07-05
  Time: 17:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>实现从内置对象中获取数据并打印</title>
</head>
<body>
<%
    /*pageContext.setAttribute("name1","pageContext对象中的属性值：张飞");
    request.setAttribute("name2","request对象中的属性值：关羽");
    session.setAttribute("name3","session对象中的属性值：刘备");
    application.setAttribute("name4","application对象中的属性值：赵云");*/

    pageContext.setAttribute("name","pageContext对象中的属性值：张飞");
    request.setAttribute("name","request对象中的属性值：关羽");
    session.setAttribute("name","session对象中的属性值：刘备");
    application.setAttribute("name","application对象中的属性值：赵云");
%>
<%--使用jsp的原始方式获取数据和打印--%>
<%--<%= "name1的数值为：" + pageContext.getAttribute("name1")%><br/>
<%= "name2的数值为：" + request.getAttribute("name2")%><br/>
<%= "name3的数值为：" + session.getAttribute("name3")%><br/>
<%= "name4的数值为：" + application.getAttribute("name4")%><br/>--%>
<%--使用EL表达式实现获取数据和打印--%>
<%--
name1的数值为：${name1} <br/>
name2的数值为：${name2} <br/>
name3的数值为：${name3} <br/>
name4的数值为：${name4} <br/>
--%>

name的数值为：${name} <br/>
name的数值为：${name} <br/>
name的数值为：${name} <br/>
name的数值为：${name} <br/>
</body>
</html>
