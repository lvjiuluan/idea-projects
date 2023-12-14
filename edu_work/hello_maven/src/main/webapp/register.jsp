<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2023-07-12
  Time: 10:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<script src="jquery-3.4.1.min.js"></script>
<script>
    $(function () {
        $("#username").blur(function () {
            // 获取用户名
            var name = $(this).val();
            // 判断用户名不为空和空串
            if (name != null && name != "") {
                // 向后台发送请求验证用户名
                $.ajax({
                    url: "/checkname",
                    type: "GET",
                    data: {username: name},
                    dataType: "json", // 响应的数据类型
                    success: function (data) {
                        console.log(data + "," + data.flag+ "," + data.msg)
                        if (data.flag == true) {
                            // $("#spanMsg").html("<font color='red'" + data.msg + "</font>");
                            $("#spanMsg").html(data.msg)
                        } else if (data.flag == false) {

                            // $("#spanMsg").html("<font color ='green'" + data.msg + "</font>");
                            $("#spanMsg").html(data.msg)
                        }else {
                            console.log("运行到最后一个else")
                        }
                    },
                    error: function () {
                        alert("请求处理失败");
                    }
                })
            }
        })
    })
</script>
<body>
<form action="#" method="post">
    用户名: <input type="text" id="username" name="username" placeholder="请输入用户名">
    <span id="spanMsg"></span>
    <br>
    密码: <input type="text" id="password" name="password" placeholder="请输入密码"><br>

</form>
</body>
</html>
