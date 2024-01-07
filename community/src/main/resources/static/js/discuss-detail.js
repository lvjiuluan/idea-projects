$(function () {
    $("#replayCommentButton").click(sendReplayComment)
    $("#replayCommentTargetUserButton").click(sendreplayCommentTargetUser)
});

/*
    * 获取
    *   private Integer postId;
    *
    *   private Integer entityType;

        private Integer entityId;

        private Integer targetId;

        private String content;
    *
    * */
function getPostId() {
    var postId = $("#getPostId").data("postid");
    return postId;
}

function getCommentId() {
    var commentId = $("#getCommentId").data("getcommentid");
    return commentId;
}

function getTargetId() {
    var targetId = $("#getTargetId").data("gettargetid");
    return targetId;
}

// 截取表单的提交事件
$("#replyPostForm").submit(function (event) {
    // 阻止默认提交事件
    event.preventDefault();
    // 自定义提交事件
    sendReplayPost();
});

function sendReplayPost() {
    var entityType = 1;
    var postId = getPostId();
    var entityId = postId;
    var content = $("#replyPostTextarea").val();

    $.post(
        CONTEXT_PATH + "/comment/add",
        {
            "postId": postId,
            "entityType": entityType,
            "entityId": entityId,
            "content": content
        },
        function (data) {
            data = $.parseJSON(data);
            console.log(data);
            if (data.code == 0) {
                window.location.reload();
            }
        }
    );

}

function sendReplayComment() {
    var entityType = 2;
    var postId = getPostId();
    var entityId = getCommentId();
    var content = $("#replayCommentInput").val();
    postData = {
        "postId": postId,
        "entityType": entityType,
        "entityId": entityId,
        "content": content
    };
    console.log(postData);
    $.post(
        CONTEXT_PATH + "/comment/add",
        postData,
        function (data) {
            data = $.parseJSON(data);
            console.log(data);
            if (data.code == 0) {
                window.location.reload();
            }
        }
    );
}

function sendreplayCommentTargetUser() {
    var entityType = 2;
    var postId = getPostId();
    var entityId = getCommentId();
    var targetId = getTargetId();
    var content = $("#replayCommentTargetUserInput").val();
    postData = {
        "postId": postId,
        "entityType": entityType,
        "entityId": entityId,
        "targetId": targetId,
        "content": content
    };
    console.log(postData);
    $.post(
        CONTEXT_PATH + "/comment/add",
        postData,
        function (data) {
            data = $.parseJSON(data);
            console.log(data);
            if (data.code == 0) {
                window.location.reload();
            }
        }
    );
}