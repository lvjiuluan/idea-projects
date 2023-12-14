<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2023-07-12
  Time: 9:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<script src="jquery-3.4.1.min.js"></script>
<script>
    function run() {
        // 1 url
        var url = "/login";
        // 2 数据
        var data = {username: "jacke"};
        // 3 发送get请求
        $.get(url, data, function (param) {
            alert("GET 响应成功：" + param);
        }, "text");
    }

    // POST方式
    function run2() {
        // 1 url
        var url = "/login";
        // 2 数据
        var data = {username: "rose"};
        // 3 发送get请求
        $.post(url, data, function (param) {
            alert("GET 响应成功：" + param);
        }, "text");
    }

    function run3() {
        $.ajax({
            url: "/login",
            async: true, // 是否异步
            data: {username: "fancy"},
            type: "POST", // 请求方式
            data_type: "text", // 返回数据的数据类型
            success: function (param) {
                alert("响应成功："+param)
            },
            error:function (param){
                alert("响应失败")
            }
        })
    }
</script>
<input type="button" value="JQuery方式发送异步请求,GET方式" onclick="run()">
<hr>
<input type="button" value="JQuery方式发送异步请求,POST方式" onclick="run2()">
<hr>
<input type="button" value="JQuery方式发送异步请求,AJAX方式" onclick="run3()">
<hr>
<body>

</body>
</html>
