<%@ page import="java.util.Map" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="java.net.http.HttpRequest" %>
<%@ page import="java.lang.reflect.Field" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2023-07-05
  Time: 17:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>EL请求参数获取</title>
</head>
<body>
<%--使用jsp的原始方式获取参数值--%>
<%--<%
    request.setCharacterEncoding("UTF-8");
    Map<String, String[]> parameterMap = request.getParameterMap();
    Set<Map.Entry<String, String[]>> entries = parameterMap.entrySet();
    for (Map.Entry<String, String[]> entry : entries) {
        System.out.println(entry.getKey() + "=" + Arrays.toString(entry.getValue()));
%>
<h1><%=entry.getKey() + "=" + Arrays.toString(entry.getValue())%></h1>
<%
    }
%>--%>

<%--使用EL表达式方式获取请求参数值--%>
<%
    request.setCharacterEncoding("UTF-8");
    Class<?> clazz = request.getClass();
    Field[] declaredFields = clazz.getDeclaredFields();
    for (Field declaredField : declaredFields) {
        declaredField.setAccessible(true);
        System.out.println(declaredField.getName());
    }

%>
<h1>姓名是：${param.name}</h1> <br/>
<h1>爱好是：${paramValues.hobby[0]}</h1> <br/>
</body>
</html>
