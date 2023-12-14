<%@ page import="java.util.LinkedList" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2023-07-05
  Time: 18:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>实现集合中数据内容的获取和打印</title>
</head>
<body>
<%
    LinkedList<Object> list = new LinkedList<>();
    list.add("one");
    list.add("two");
    list.add("three");

    request.setAttribute("list",list);

    Map<String,Integer> map = new HashMap<>();
    map.put("one",1);
    map.put("two",2);
    map.put("th_.ree",3);
    request.setAttribute("map",map);
%>
集合中下表为0的元素为：${list[0]}
<br>
集合中下表为1的元素为：${list.get(1)}
<br>
集合中下表为2的元素为：${list[2]}
<br>
map中的元素有：${map}
<br>
带有特殊字符key对应的值为：${map["th_.ree"]}
<br>

</body>
</html>
