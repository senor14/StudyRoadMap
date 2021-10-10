<%--
  User: 김창영
  Date: 2021-10-03
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

<form action="/RoadMap/PassWordChangeProc" method="post" onsubmit="return check();">
    <span>PASSWORD CHANGE</span><br>
    <span>PASSWORD</span>
    <input type="password" name="pwd" id="newPassWord" placeholder="Type your Password" required/><br>
    <span>PASSWROD CHECK</span>
    <input type="password" name="pwd2" id="passWordCheck" placeholder="Type your Password Check" required/><br>
    <span id="renew"></span><br>
    <button type="submit">확인</button>
    <button type="button">취소</button>
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

    let pwdCheck = 'N';

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


    function check() {
        if(pwdCheck == "N"){
            alert("비밀번호가 일치하지 않습니다. 비밀번호를 확인해주세요.");
            return false;
        } else{
            return true;
        }
    }
</script>
</html>
