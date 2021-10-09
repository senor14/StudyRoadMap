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

<form action="/RoadMap/userWithdrawalProc" method="post" onsubmit="return check();" id="login_form">
    <span>Withdrawal</span><br>
    <span>Withdrawal Check</span><<br>
    <span>회원 탈퇴를 누르면 다시 되돌릴 수 없습니다. 탈퇴를 진행하시려면 Account_withdrawal를 입력해주세요.</span><br>
    <input type="text" name="DeleteCheck" id="DeleteCheck" placeholder="Account_withdrawal" required/><br>
    <span id="id_find"></span><br>
    <button type="submit">탈퇴하기</button>
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

    let DeleteCheck = 'N';

    $("#DeleteCheck").keyup(function() {
        let query = {
            DeleteCheck : $("#DeleteCheck").val()
        };

        $.ajax({
            url : "/RoadMap/userWithdrawalCheck",
            type : "post",
            data : query,
            success : function(data) {
                if (data == 1) {
                    $("#id_find").text("탈퇴 확인 문자열이 같습니다.");
                    $("#id_find").attr("style", "color:#00f");
                    DeleteCheck = 'Y'
                } else {
                    $("#id_find").text("탈퇴 확인 문자열이 다릅니다.");
                    $("#id_find").attr("style", "color:#f00");
                    DeleteCheck = 'N'
                }
            }
        }); // ajax 끝
    });


    function check() {
        if(idCheck == 'N'){
            alert("탈퇴 확인 문구가 다릅니다. 탈퇴 확인 문구를 확인해주세요.");
            return false;
        } else{
            return true;
        }
    }
</script>
</html>
