//提交回复
function comment_from_question() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    comment_post(questionId, 1, content);
}

function comment_from_comment(commentId) {
    var content = $("#comment-sub-content-" + commentId).val();
    comment_post(commentId, 2, content);
}

function comment_post(parentId, parentType, content) {
    if (!content) {
        alert("回复内容不允许为空！");
        return;
    }

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/comment",
        data: JSON.stringify({
            "parentId": parentId,
            "content": content,
            "type": parentType
        }),
        success: function (response) {
            if (response.code == 200) {
                //$("#comment_section").hide();
                window.location.reload();
            } else if (response.code == 2003) {
                var isAccept = confirm(response.message);
                if (isAccept) {
                    window.open("https://github.com/login/oauth/authorize?client_id=9a0b063322847b377cc1&redirect_uri=http://localhost:2020/callback&scope=user&state=1");
                    window.localStorage.setItem("closable", true);
                }
            } else {
                alert(response.message);
            }
        },
        dataType: "json"
    })
}

//分页展示二级回复
function collapseCommentsByNav(parentId, page) {
    document.getElementById("commentList2-"+ parentId).remove();
    document.getElementById('comment-pagination-'+parentId).remove();
    getSubComment(parentId, page);
}

//展开二级回复
function collapseComments(parentId, page) {
    var subCommentContainer = document.getElementById("comment-" + parentId);
    if (subCommentContainer.classList.contains("in")) {
        subCommentContainer.classList.remove("in");
    } else {
        if (document.getElementById("commentList-" + parentId).children.length == 0) {
            //展开前获取数据
            getSubComment(parentId, page);
        }
        subCommentContainer.classList.add("in");
    }
}

function getSubComment(parentId, page){
    $.getJSON("/comment/" + parentId + '?page=' + page + '&size=1', function (returnData) {
        var pagination = returnData.data;

        //循环显示二级评论
        var commentListElement = $("<div/>", {"id": "commentList2-" + parentId});
        $.each(pagination.datas, function (index, comment) {
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
                "id":"content-"+index,
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

            var spElement = $("<hr>", {
                "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comment-sub-sp"
            });

            var commentElement = $("<div/>", {
                "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments",
            }).append(mediaElement).append(spElement);
            commentListElement.append(commentElement);
        });
        $("#commentList-" + parentId).prepend(commentListElement);

        //二级评论页码
        var navElement = $("<ul/>", {
            "class":"pagination",
            "id":"comment-pagination-"+parentId
        });
        if(pagination.showFirstPage){
            navElement
                .append($("<li/>")
                    .append($("<span/>", {
                        "onclick":"collapseCommentsByNav("+ parentId +",1)",
                        "aria-hidden": "true",
                        "text":"<<",
                        "aria-label":"firstpage"
                    }))
                );
        }
        if(pagination.showPrevious){
            navElement
                .append($("<li/>")
                    .append($("<span/>", {
                        "onclick":"collapseCommentsByNav("+ parentId +","+ pagination.page-1 +")",
                        "aria-hidden": "true",
                        "text":"<",
                        "aria-label":"Previous"
                    }))
                );
        }
        $.each(pagination.pages, function (index, curpage) {
            if(curpage == pagination.page){
                navElement
                    .append($("<li/>")
                        .append($("<span/>", {
                            "onclick":"collapseCommentsByNav("+ parentId +","+ curpage +")",
                            "aria-hidden": "true",
                            "class":"active",
                            "text": curpage,
                            "aria-label": curpage
                        }))
                    );
            } else{
                navElement
                    .append($("<li/>")
                        .append($("<span/>", {
                            "onclick":"collapseCommentsByNav("+ parentId +","+ curpage +")",
                            "aria-hidden": "true",
                            "text": curpage,
                            "aria-label": curpage
                        }))
                    );
            }
        })
        if(pagination.showNext){
            navElement
                .append($("<li/>")
                    .append($("<span/>", {
                        "onclick":"collapseCommentsByNav("+ parentId +","+ pagination.page+1 +")",
                        "aria-hidden": "true",
                        "text":">",
                        "aria-label":"Next"
                    }))
                );
        }
        if(pagination.showEndPage){
            navElement
                .append($("<li/>")
                    .append($("<span/>", {
                        "onclick":"collapseCommentsByNav("+ pagination.totalPage +",1)",
                        "aria-hidden": "true",
                        "text":">>",
                        "aria-label":"endpage"
                    }))
                );
        }
        $("#comment-nav-" + parentId).append(navElement);
    });
}

function selectTag(value) {
    var orignValue = $("#tag").val();
    if(orignValue != null && orignValue != ""){
        var values = orignValue.split(",");
        if(values.indexOf(value) >= 0){
            while (values.indexOf(value) >= 0){
                values.splice(values.indexOf(value), 1);
            }
        } else {
            values.push(value);
        }
        $("#tag").val(values.join(","));
    } else {
        $("#tag").val(value);
    }
}
