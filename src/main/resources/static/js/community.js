function comment_post() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    console.log(questionId);
    console.log(content);

    $.ajax({
        type:"POST",
        contentType:"application/json",
        url:"/comment",
        data: JSON.stringify({
            "parentId":questionId,
            "content":content,
            "type":1
        }),
        success: function (response) {
            if(response.code == 200){
                $("#comment_section").hide();
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