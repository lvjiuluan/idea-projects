$(function () {
    $("#publishBtn").click(publish);
});

function publish() {
    $("#publishModal").modal("hide");
    // 1 获取标题和内容 recipient-name
    var title = $("#recipient-name").val();
    var content = $("#message-text").val();
    // 2 发送异步请求
    $.post(
        CONTEXT_PATH + "/post/add",
        {
            "title": title,
            "content": content
        },
        function (data) {
            // 解析成对象
            data = $.parseJSON(data);
            // 把提示消息写入提示框
            $("#hintBody").text(data.msg);
            // 帖子发送提示框
            $("#hintModal").modal("show");
            // 2s后自动隐藏提示框
            setTimeout(function () {
                $("#hintModal").modal("hide");
                // 如果成功就刷新页面
                // 失败就不管
                if (data.code == 0) {
                    window.location.reload();
                }
            }, 2000);
        }
    )


}