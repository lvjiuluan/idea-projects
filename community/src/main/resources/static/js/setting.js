$(function () {
    $("#uploadForm").submit(upload);
});

function upload() {

    $.ajax({
        url: "http://up-z0.qiniup.com",
        method: "post",
        processData: false,  //不以字符串上传
        contentType: false,
        data: new FormData($("#uploadForm")[0]),
        success: function (data) {
            if (data && data.code == 0) {
                // 更新头像访问路径
                console.log(data)
                $.post(
                    CONTEXT_PATH + "/header/url",
                    {"fileName": $("input[name = 'key']").val()},
                    function (data) {
                        data = $.parseJSON(data);
                        if (data.code == 0) {
                            // window.location.reload();
                        } else {
                            alert(data.msg);
                        }
                    }
                );
            } else {
                alert("上传失败");
            }
        },
    });
    return false; // 表示事件到此为止，不在执行原form的提交事件
}