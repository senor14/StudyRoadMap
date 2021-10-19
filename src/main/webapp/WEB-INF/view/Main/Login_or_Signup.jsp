<%--
  User: shfkf
  Date: 2021-10-05
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/login_style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/study_mindMap/modal.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/study_mindMap/study_mindMap.css">

    <link rel="shortcut icon" href="#">
    <title>Login Template</title>
</head>

<body translate="no">
    <div class="form-structor">
        <h2 class="main-title">RoadMap</h2>
        <div class="signup"><br>
        <h2 class="form-title" id="signup"><span>or</span>Sign up</h2>
        <form action="/RoadMap/SignUpProc.do" method="post" onsubmit="return check();" id="login_form">
            <div class="form-holder">
                <input type="text" class="input" name="id" id="userId" placeholder="Type your ID" minlength="6" required/>
                <span id="id_find"></span>
                <input type="password" class="input" name="pwd" id="newPassWord" placeholder="Type your Password" required/>
                <input type="password" class="input" name="pwd2" id="passWordCheck" placeholder="Type your Password Check" required/>
                <span id="renew"></span>
                <input type="email" class="input" name="email" id="userEmail" placeholder="Type your email" required/>
                <button type="button" class="lil-btn" onclick="create_auth_num();">전송</button>
                <span id="email_find"></span>
                <input type="text" class="input" name="auth" id="auth" numberOnly="true" maxlength="6" placeholder="Type your Authentication Code" required/>
                <button type="button" class="lil-btn" onclick="certified_auth_num()">확인</button>
                <span id="auth_find"></span>
            </div>
            <button type="submit" class="submit-btn">SIGN UP</button>
        </form>
    </div>
    <div class="login slide-up">
        <div class="center">
            <h2 class="form-title" id="login"><span>or</span>Log in</h2>
            <form action="/RoadMap/LoginProc" method="post">
                <div class="form-holder">
                    <input type="text" class="input" name="id" placeholder="Type your ID" />
                    <input type="password" class="input" name="pwd" placeholder="Type your password"/>
                </div>
                    <button type="submit" class="submit-btn">Login</button>
            </form>
            <div style="display: flex; justify-content: space-between">
                <div>
                    <button type="button" class="lil-btn2" onclick="fnOpenModal('#m2-o')" style="width: 110px;">아이디 찾기</button><br>
                </div>
                <div>
                    <button type="button" class="lil-btn2" onclick="fnOpenModal('#m3-o')" style="width: 110px;">비밀번호 찾기</button><br>
                </div>
            </div>
        </div>
    </div>
</div>
    <%-- modal 기본 --%>
    <div class="modal-container" id="m2-o" style="--m-background: hsla(0, 0%, 0%, .4);">
        <div class="modal">
            <h1 class="modal__title" id="modal__title">아이디 찾기</h1>
            <div>
                <form action="/RoadMap/SendId" method="post" id="id_find_form">
                    <span>EMAIL</span>
                    <input type="text" name="userEmail" placeholder="Type your EMAIL" /><br>
                </form>
                <button type="button" class="modal__btn" onclick="id_find();">확인</button>
                <button type="button" class="modal__btn" onclick="fnCloseModal('#m2-o');" >취소</button>
            </div>
            <button class="modal__btn" onclick="fnOpenModal('#m3-o'); fnCloseModal('#m2-o');">비밀번호 찾기</button>
        </div>
    </div>
    <%-- modal 기본 끝 --%>

    <%-- modal 추가 --%>
    <div class="modal-container" id="m3-o" style="--m-background: hsla(0, 0%, 0%, .4);">
        <div class="modal">
            <h1 class="modal__title">비밀번호 찾기</h1>
                <div>
                    <form action="/RoadMap/ReMakePW" method="post" id="pwd_find_form">
                        <span>EMAIL:</span>
                        <input type="text" name="userEmail" placeholder="Type your EMAIL" /><br>
                        <span>ID:</span>
                        <input type="text" name="userId" placeholder="Type your ID" /><br>
                    </form>
                    <button type="button" class="modal__btn" onclick="pwd_find();">확인</button>
                    <button type="button" class="modal__btn" onclick="fnCloseModal('#m3-o');" >취소</button>
                </div>
            <button class="modal__btn" onclick="fnOpenModal('#m2-o'); fnCloseModal('#m3-o');">아이디 찾기</button>
        </div>
    </div>
    <%-- modal 추가 끝 --%>

</body>


<script src="https://cpwebassets.codepen.io/assets/common/stopExecutionOnTimeout-1b93190375e9ccc259df3a57c1abc0e64599724ae30d7ea4c6877eb615f89387.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/login_main.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery-migrate-3.0.1.min.js"></script>
<script>

    let idCheck = 'N';
    let emailCheck = 'N';
    let pwdCheck = 'N';
    let createAuth = 'N';
    let authenticationCertified = 'N';

    $("#userId").keyup(function() {
        let query = {
            user_id : $("#userId").val()
        };

        $.ajax({
            url : "/RoadMap/idCheck",
            type : "post",
            data : query,
            success : function(data) {
                if (data == 2) {
                    $("#id_find").text("6자리 이상 영문 혹은 영문+숫자로 설정하세요.");
                    $("#id_find").attr("style", "color:#f00;font-size: 5px;padding-left:13px;");
                    idCheck = 'N'
                } else if (data == 1) {
                    $("#id_find").text("사용하고 있는 아이디입니다.");
                    $("#id_find").attr("style", "color:#f00;font-size: 5px;padding-left:13px;");
                    idCheck = 'N'
                } else {
                    $("#id_find").text("사용 가능한 아이디입니다.");
                    $("#id_find").attr("style", "color:#00f;font-size: 5px;padding-left:13px;");

                    //$('#userId').attr("disabled", true);
                    idCheck = 'Y'
                }
            }
        }); // ajax 끝
    });

    $('#passWordCheck').keyup(function() {

        let pw = document.getElementById("newPassWord").value; //비밀번호
        let pw2 = document.getElementById("passWordCheck").value; // 확인 비밀번호

        if (pw != "" || pw2 != "") {
            if (pw == pw2) {
                $("#renew").text("비밀번호가 일치합니다.");
                $("#renew").attr("style", "color:#00f;font-size: 5px;padding-left:13px;");
                pwdCheck = 'Y';
            } else {
                $("#renew").text("비밀번호가 일치하지 않습니다.");
                $("#renew").attr("style", "color:#f00;font-size: 5px;padding-left:13px;");
                pwdCheck = 'N';
            }
        }

    })

    //이메일 중복확인
    $("#userEmail").keyup(function() {
        let query = {
            user_email : $("#userEmail").val()
        };

        $.ajax({
            url : "/RoadMap/emailCheck.do",
            type : "post",
            data : query,
            success : function(data) {

                if (data == 2) {
                    $("#email_find").text("올바르지 않은 이메일 형식입니다.");
                    $("#email_find").attr("style", "color:#f00;font-size: 5px;padding-left:13px;");
                    emailCheck = 'N';
                } else if (data == 1) {
                    $("#email_find").text("이미 사용 중인 이메일입니다.");
                    $("#email_find").attr("style", "color:#f00;font-size: 5px;padding-left:13px;");
                    emailCheck = 'N';
                } else{
                    $("#email_find").text("사용 가능한 이메일입니다.");
                    $("#email_find").attr("style", "color:#00f;font-size: 5px;padding-left:13px;");
                    emailCheck = 'Y';
                }
            }
        }); // ajax 끝
    });

    $(document).on("keyup", "input:text[numberOnly]", function() {
        $(this).val( $(this).val().replace(/[^0-9]/gi,"") );
    });

    function create_auth_num(){

        if(emailCheck == 'Y') {
            let query = {
                user_email: $("#userEmail").val()
            };

            console.log("user_email", query.user_email);

            $("#email_find").text("인증번호를 이메일로 전송 중입니다.");
            $("#email_find").attr("style", "color:#aaa;font-size: 5px;padding-left:13px;");
            $.ajax({
                url: "/RoadMap/emailAuth",
                type: "post",
                data: query,
                success: function (data) {

                    if (data == 1) {
                        createAuth = 'Y';
                        $("#email_find").text("인증번호가 이메일로 전송되었습니다.");
                        $("#email_find").attr("style", "color:#00f;font-size: 5px;padding-left:13px;");
                    } else {
                        createAuth = 'N';
                        $("#email_find").text("인증번호 전송에 실패했습니다.");
                        $("#email_find").attr("style", "color:#f00;font-size: 5px;padding-left:13px;");
                    }
                }
            }); // ajax 끝
        } else {
            createAuth = 'N';
            $("#email_find").text("사용 중이거나 올바르지 않은 이메일입니다.");
            $("#email_find").attr("style", "color:#f00;font-size: 5px;padding-left:13px;");
        }
    }

    function certified_auth_num() {
        if(createAuth == 'Y'){
            let query = {
                user_email : $("#userEmail").val(),
                auth_num : $("#auth").val()
            };

            $.ajax({
                url: "/RoadMap/emailCertified",
                type: "post",
                data: query,
                success: function (data) {

                    if (data == 1) {
                        authenticationCertified = 'Y';
                        $("#email_find").text("인증번호가 확인되었습니다.");
                        $("#email_find").attr("style", "color:#00f;font-size: 5px;padding-left:13px;");
                    } else {
                        authenticationCertified = 'N';
                        $("#email_find").text("인증번호가 다릅니다.");
                        $("#email_find").attr("style", "color:#f00;font-size: 5px;padding-left:13px;");
                    }
                }
            }); // ajax 끝
        } else {
            authenticationCertified = 'N';
            $("#email_find").text("전송 버튼을 눌러주세요.");
            $("#email_find").attr("style", "color:#f00;font-size: 5px;padding-left:13px;");
        }
    }

    function check() {
        if (emailCheck == 'N') {
            alert("사용 중이거나 올바르지 않은 이메일입니다. 이메일을 변경해주세요.");
            return false;
        } else if(idCheck == 'N'){
            alert("사용 중이거나 올바르지 않은 아이디입니다. 아이디를 변경해주세요.");
            return false;
        } else if(pwdCheck == "N"){
            alert("비밀번호가 일치하지 않습니다. 비밀번호를 확인해주세요.");
            return false;
        } else if(createAuth == 'N'){
            alert("이메일을 인증하지 않았습니다. 이메일을 인증해주세요.");
            return false;
        } else if(authenticationCertified == 'N'){
            alert("인증번호를 확인해주세요.");
            return false;
        } else{
            return true;
        }
    }
</script>
<%-- 모달 조작 함수 --%>
<script>
    // 모달 오픈
    function fnOpenModal(id){
        // $('#m2-o').css("display", "flex");
        if (id === '#m4-o' || id === '#m5-o') {
            id = '#m6-o'
        }
        $(id).css("display", "flex");
    }
    // 모달 종료
    function fnCloseModal(id){
        //$('#m2-o').css("display", "none");
        $(id).css("display", "none");
    }

</script>
<!--폼 관련 파일-->
<script>
    function id_find() {

        document.getElementById('id_find_form').submit();

    }
    function pwd_find() {

        document.getElementById('pwd_find_form').submit();

    }
    window.onload = function page_check() {
        <%int check = 0;
            if(session.getAttribute("SS_USER_ID") == null){
            check = 1;
        }%>
        let check = <%=check%>;
        console.log("check: ", check);
        if (check == 0) {
            location.href = "/index";
        }
    }
    window.onpageshow = function (event) {
        if(event.persisted || (window.performance && window.performance.navigation.type == 2)) {
            location.reload()
        }
    }
</script>
</html>