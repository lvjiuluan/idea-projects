// $(function () {
//     $("#replayCommentButton").click(sendReplayComment)
//     $("#replayCommentTargetUserButton").click(sendreplayCommentTargetUser)
// });

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

function getCommentId(index) {
    var commentId = $("#getCommentId" + index).data("getcommentid");
    return commentId;
}

function getTargetId(index) {
    var targetId = $("#getTargetId" + index).data("gettargetid");
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

function sendReplayComment(id) {
    var index = id.match(/\d+/)[0]
    var entityType = 2;
    var postId = getPostId();
    var entityId = getCommentId(index);
    var content = $("#replayCommentInput" + index).val();
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

function sendreplayCommentTargetUser(element) {
    id = element.id
    var index = id.match(/\d+/)[0]
    var commentIndex = element.dataset.commentindex

    var entityType = 2;
    var postId = getPostId();
    var entityId = getCommentId(commentIndex);
    var targetId = getTargetId(index);
    var content = $("#replayCommentTargetUserInput" + index).val();
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

function like(element, entityType, entityId, entityUserId, postId) {
    $.post(
        CONTEXT_PATH + "/like",
        {
            "entityType": entityType,
            "entityId": entityId,
            "entityUserId": entityUserId,
            "postId": postId
        },
        function (data) {
            data = $.parseJSON(data);
            console.log(data);
            if (data.code == 0) {
                $(element).children("i").text(data.likeCount)
                $(element).children("b").text(data.likeStatus == 1 ? "已赞" : "赞")
            } else {
                alert(data.msg);
            }
        }
    )
}


function topPost() {
    console.log("置顶")
    postId = getPostId();
    console.log(postId);
    $.post(
        CONTEXT_PATH + "/top",
        {"postId": postId},
        function (data) {
            data = $.parseJSON(data);
            console.log(data);
            if (data.code == 0) {
                window.location.reload();
            }
        }
    )
}

function highlight() {
    console.log("加精")
    postId = getPostId();
    console.log(postId);
    $.post(
        CONTEXT_PATH + "/highlight",
        {"postId": postId},
        function (data) {
            data = $.parseJSON(data);
            console.log(data);
            if (data.code == 0) {
                window.location.href = CONTEXT_PATH + "/index";
            }
        }
    )
}

function deletePost() {
    console.log("删除")
    postId = getPostId();
    console.log(postId);
    $.post(
        CONTEXT_PATH + "/delete",
        {"postId": postId},
        function (data) {
            data = $.parseJSON(data);
            console.log(data);
            if (data.code == 0) {
                window.location.href = CONTEXT_PATH + "/index";
            }
        }
    )
}