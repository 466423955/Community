//提交回复
function comment_from_question() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    comment_post(questionId, 1, content);
}

function comment_from_comment(commentId) {
    var content = $("#comment-sub-content-"+commentId).val();
    comment_post(commentId, 2, content);
}

function comment_post(parentId, parentType, content) {
    if(!content){
        alert("回复内容不允许为空！");
        return;
    }

    $.ajax({
        type:"POST",
        contentType:"application/json",
        url:"/comment",
        data: JSON.stringify({
            "parentId":parentId,
            "content":content,
            "type":parentType
        }),
        success: function (response) {
            if(response.code == 200){
                //$("#comment_section").hide();
                window.location.reload();
            }else if(response.code == 2003){
                var isAccept = confirm(response.message);
                if(isAccept){
                    window.open("https://github.com/login/oauth/authorize?client_id=9a0b063322847b377cc1&redirect_uri=http://localhost:2020/callback&scope=user&state=1");
                    window.localStorage.setItem("closable",true);
                }
            }else {
                alert(response.message);
            }
        },
        dataType:"json"
    })
}

//展开二级回复
function collapseComments(parentId) {
    var subCommentContainer = document.getElementById("comment-" + parentId);
    debugger;
    if(subCommentContainer.classList.contains("in")){
        subCommentContainer.classList.remove("in");
    } else {
        if (subCommentContainer.children.length == 1) {
            //展开前获取数据
            $.getJSON("/comment/" + parentId, function (data) {
                $.each(data.data.reverse(), function (index, comment) {
                    var mediaLeftElement = $("<div/>", {
                        "class": "media-left"
                    }).append($("<img/>", {
                        "class": "media-object img-rounded",
                        "src": comment.user.avatarurl
                    }));

                    var mediaBodyElement = $("<div/>", {
                        "class": "media-body"
                    }).append($("<h5/>", {
                        "class": "media-heading",
                        "html": comment.user.name
                    })).append($("<div/>", {
                        "class": "comment-content",
                        "html": comment.description
                    })).append($("<div/>", {
                        "class": "comment-menu"
                    }).append($("<span/>", {
                        "class": "pull-right community-time ",
                        "html": moment(comment.gmtCreate).format('YYYY-MM-DD HH:mm:ss')
                    })));

                    var mediaElement = $("<div/>", {
                        "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comment-sp-top media"
                    }).append(mediaLeftElement).append(mediaBodyElement);

                    var spElement = $("<hr>",{
                        "class" : "col-lg-12 col-md-12 col-sm-12 col-xs-12 comment-sub-sp"
                    });

                    var commentElement = $("<div/>", {
                        "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments"
                    }).append(mediaElement).append(spElement);
                    $("#comment-" + parentId).prepend(commentElement);
                });
            });
        }
        subCommentContainer.classList.add("in");
    }
}