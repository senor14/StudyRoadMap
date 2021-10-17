let container
let roadMapId
$(document).ready(function(){
    container = document.getElementsByClassName("user_comment")[0]
    let href = window.location.href.split("/")
    roadMapId = href[href.length]
    findMyComment()
})

function getComment(myComments){

    $(".user_comment").empty();
    $.ajax({
        url: "/getComment/"+roadMapId,
        type: "GET",
        dataType: "json",
        success : function(data) {
            if(JSON.stringify(data) !== "[]") {
                if(myComments !== "[]") {

                    data.forEach(comment =>{
                        if(myComments.includes(comment.commentId)){
                            let newComment = '<li>'
                                +'<div class="comment_body my_comment">'
                                    +'<div class="replied_to">'
                                        +'<span class="user">'
                                            +comment.userId+': '
                                        +'</span>'
                                        +comment.commentContents
                                    +'</div>'
                                    +'<div class="comment_details my_comment">'
                                        +'<ul data-id="'+comment.commentId+'">'
                                            +'<li class="delete" onclick="deleteComment(this)">'
                                                +'<i class="fa fa-trash"></i>'
                                                +'삭제'
                                            +'</li>'
                                            +'<li>'
                                                +'<i class="fa fa-pencil"></i>'
                                                +comment.userId
                                            +'</li>'
                                            +'<li>'
                                                +'<i class="fa fa-clock-o"></i>'
                                                +comment.created.split("/")[1]
                                            +'</li>'
                                            +'<li>'
                                                +'<i class="fa fa-calendar"></i>'
                                                + comment.created.split("/")[0]
                                            +'</li>'
                                        +'</ul>'
                                    +'</div>'
                                +'</div>'
                                +'</li>'
                            container.innerHTML += newComment
                        }else{
                            let newComment = '<li>'
                                +'<div class="comment_body">'
                                    +'<div class="replied_to">'
                                        +'<span class="user">'
                                            +comment.userId+': '
                                        +'</span>'
                                        +comment.commentContents
                                    +'</div>'
                                    +'<div class="comment_details">'
                                        +'<ul data-id="'+comment.commentId+'">'
                                            +'<li>'
                                                +'<i class="fa fa-calendar"></i>'
                                                + comment.created.split("/")[0]
                                            +'</li>'
                                            +'<li>'
                                                +'<i class="fa fa-clock-o"></i>'
                                                +comment.created.split("/")[1]
                                            +'</li>'
                                            +'<li>'
                                                +'<i class="fa fa-pencil"></i>'
                                                +comment.userId
                                            +'</li>'
                                        +'</ul>'
                                    +'</div>'
                                +'</div>'
                                +'</li>'
                            container.innerHTML += newComment
                        }

                    })
                }else{

                }
            }
            // console.log("실행 완료 ", JSON.stringify(data));
            // console.log("실행 완료 data length-", data.length);
        },
        error : function(err) {
            console.log("실행중 오류가 발생하였습니다. 에러:",err);
        }
    })
}

function findMyComment(){
    $.ajax({
        url: "/findMyComment/"+roadMapId,
        type: "GET",
        dataType: "json",
        success : function(data) {
            if(JSON.stringify(data) !== "[]") {
                let myComments = []
                data.forEach(id =>{
                    myComments.push(id.commentId)
                })
                getComment(myComments)
            }
            // console.log("실행 완료 ", JSON.stringify(data));
            // console.log("실행 완료 data length-", data.length);
        },
        error : function(err) {
            console.log("실행중 오류가 발생하였습니다. 에러:",err);
        }
    })
}

function insertComment(){
    $.ajax({
        url: "/insertComment/"+roadMapId,
        type:"GET",
        dataType : "json",
        data : {comment : document.getElementsByClassName("comment")[0].value},
        success : function(data) {
            if(JSON.stringify(data)) {
                console.log("실행 완료 ", JSON.stringify(data))
                findMyComment()
            } else{
                console.log("문제 발생 작성안됨")
            }
        },
        error : function(err) {
            console.log("실행중 오류가 발생하였습니다. 에러:",err);
        }
    })
    getComment();
    document.querySelector(".comment").value = "";

}

function deleteComment(li){
    let ul = li.parentNode
    let commentId = ul.getAttribute("data-id")

    $.ajax({
        url: "/deleteComment.do",
        type:"GET",
        dataType : "json",
        data : {commentId : commentId},
        success : function(data) {
            if(JSON.stringify(data)) {
                // console.log("실행 완료 ", JSON.stringify(data))
            } else{
                console.log("문제 발생 삭제안됨")
            }
            findMyComment()
        },
        error : function(err) {
            console.log("실행중 오류가 발생하였습니다. 에러:",err);
        }
    })
}
