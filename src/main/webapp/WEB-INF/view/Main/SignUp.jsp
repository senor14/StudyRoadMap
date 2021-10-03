<%--
  User: 김창영
  Date: 2021-09-27
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Erase - Free Bootstrap 4 Template by Colorlib</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <link href="https://fonts.googleapis.com/css?family=Poppins:100,200,300,400,500,600,700,800,900" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Abril+Fatface&display=swap" rel="stylesheet">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/open-iconic-bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/animate.css">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/owl.carousel.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/owl.theme.default.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/magnific-popup.css">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/aos.css">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/ionicons.min.css">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap-datepicker.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery.timepicker.css">


    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/flaticon.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/icomoon.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/tab.css">
</head>

<body>

<form action="/RoadMap/SignUpProc.do" method="post" onsubmit="return check();" id="login_form">
    <span>Sign up</span><br>
    <span>ID</span>
    <input type="text" name="id" id="userId" placeholder="Type your ID" minlength="6" required/><br>
    <span id="id_find"></span><br>
    <span>PASSWORD</span>
    <input type="password" name="pwd" id="newPassWord" placeholder="Type your Password" required/><br>
    <span>PASSWROD CHECK</span>
    <input type="password" name="pwd2" id="passWordCheck" placeholder="Type your Password Check" required/><br>
    <span id="renew"></span><br>
    <span>EMAIL</span>
    <input class="input100" type="email" name="email" id="userEmail" placeholder="Type your email" required/>
    <button type="button" onclick="create_auth_num();">전송</button><br>
    <span id="email_find"></span><br>
    <span>AUTHENTICATION NUMBER</span>
    <input class="input100" type="text" name="auth" id="auth" numberOnly="true" maxlength="6" placeholder="Type your Security Code" required/>
    <button type="button" onclick="certified_auth_num()">확인</button><br>
    <span id="auth_find"></span><br>
    <button type="submit">SIGN UP</button>
    <button type="button">CANCEL</button>
</form>


<script src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery-migrate-3.0.1.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/popper.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery.easing.1.3.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery.waypoints.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery.stellar.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/owl.carousel.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery.magnific-popup.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/aos.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/tab.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery.animateNumber.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/scrollax.min.js"></script>
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBVWaKrjvy3MaE7SQ74_uJiULgl1JY0H2s&sensor=false"></script>
<script src="${pageContext.request.contextPath}/resources/js/google-map.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/main.js"></script>

</body>
</html>

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
                    $("#id_find").text("올바르지 않은 아이디 형식입니다. 6자리 이상 영문 혹은 영문+숫자로 아이디를 설정해주세요.");
                    $("#id_find").attr("style", "color:#f00");
                    idCheck = 'N'
                } else if (data == 1) {
                    $("#id_find").text("사용하고 있는 아이디입니다.");
                    $("#id_find").attr("style", "color:#f00");
                    idCheck = 'N'
                } else {
                    $("#id_find").text("사용 가능한 아이디입니다.");
                    $("#id_find").attr("style", "color:#00f");
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
                $("#renew").css("color", "#00f");
                pwdCheck = 'Y';
            } else {
                $("#renew").text("비밀번호가 일치하지 않습니다.");
                $("#renew").css("color", "#f00");
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
                    $("#email_find").attr("style", "color:#f00");
                    emailCheck = 'N';
                } else if (data == 1) {
                    $("#email_find").text("이미 사용 중인 이메일입니다.");
                    $("#email_find").attr("style", "color:#f00");
                    emailCheck = 'N';
                } else{
                    $("#email_find").text("사용 가능한 이메일입니다.");
                    $("#email_find").attr("style", "color:#00f");
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
            $("#email_find").text("인증번호를 이메일로 전송 중입니다.");
            $("#email_find").attr("style", "color:#aaa");
            $.ajax({
                url: "/RoadMap/emailAuth",
                type: "post",
                data: query,
                success: function (data) {

                    if (data == 1) {
                        createAuth = 'Y';
                        $("#email_find").text("인증번호가 이메일로 전송되었습니다.");
                        $("#email_find").attr("style", "color:#00f");
                    } else {
                        createAuth = 'N';
                        $("#email_find").text("인증번호 전송에 실패했습니다.");
                        $("#email_find").attr("style", "color:#f00");
                    }
                }
            }); // ajax 끝
        } else {
            createAuth = 'N';
            $("#email_find").text("사용중인 이메일입니다. 다른 이메일 주소를 사용해주세요.");
            $("#email_find").attr("style", "color:#f00");
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
                        $("#email_find").attr("style", "color:#00f");
                    } else {
                        authenticationCertified = 'N';
                        $("#email_find").text("인증번호가 다릅니다.");
                        $("#email_find").attr("style", "color:#f00");
                    }
                }
            }); // ajax 끝
        } else {
            authenticationCertified = 'N';
            $("#email_find").text("전송 버튼을 눌러주세요.");
            $("#email_find").attr("style", "color:#f00");
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
            alert("인증번호가 전송되지 않았습니다. 이메일을 인증해주세요.");
            return false;
        } else if(authenticationCertified == 'N'){
            alert("인증번호를 확인해주세요.");
            return false;
        } else{
            return true;
        }
    }
</script>
</html>
