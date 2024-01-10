$(function () {
    $("#sendBtn").click(send_letter);
    $(".close").click(delete_msg);
});

function send_letter() {
    $("#sendModal").modal("hide");

    var toName = $("#recipient-name").val();
    var content = $("#message-text").val();

    postData = {
        "toName": toName,
        "content": content
    };

    console.log(postData)

    $.post(
        CONTEXT_PATH + "/letter/send",
        postData,
        function (data) {
            data = $.parseJSON(data);
            console.log(data);
            $("#hintBody").text(data.msg);
            $("#hintModal").modal("show");
            setTimeout(function () {
                $("#hintModal").modal("hide");
                if (data.code == 0) {
                    location.reload();
                }
            }, 2000);

        }
    )
}

function delete_msg() {
    // TODO 删除数据
    $(this).parents(".media").remove();
}