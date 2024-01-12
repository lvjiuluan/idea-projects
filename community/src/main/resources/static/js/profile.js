$(function () {
    $(".follow-btn").click(follow);
});

function follow(index) {
    if (index == undefined) {
        var entityId = $("#entityId").val();
        var btn = this;
    } else {
        var entityId = $("#entityId" + index).val();
        var btn = $("#followButton" + index);
    }

    postData = {
        "entityType": 3,
        "entityId": entityId
    };
    console.log(postData);
    if ($(btn).hasClass("btn-info")) {
        $.post(
            CONTEXT_PATH + "/follow",
            postData,
            function (data) {
                data = $.parseJSON(data);
                if (data.code == 0) {
                    window.location.reload();
                } else {
                    console.log(data);
                }
            }
        );
        // 关注TA
        // $(btn).text("已关注").removeClass("btn-info").addClass("btn-secondary");
    } else {
        // 取消关注
        console.log("这是取消关注的方法")
        $.post(
            CONTEXT_PATH + "/unfollow",
            postData,
            function (data) {
                data = $.parseJSON(data);
                if (data.code == 0) {
                    window.location.reload();
                } else {
                    console.log(data);
                }
            }
        );
    }
}