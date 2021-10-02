<%--
  User: 김창영
  Date: 2021-09-27
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
          pageEncoding="UTF-8"%>
<script src="{pageContext.request.contextPath}/js/jquery-3.5.0.min.js"></script>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>SingUp</title>
</head>
<body>
    <form action="/RoadMap/SignUpProc.do" method="post">
        <span>Sign up</span><br>
        <span>ID</span>
        <input type="text" name="id" id="userId" placeholder="Type your ID"/><br>
        <span data-symbol="&#xf206;"></span>
        <span>PASSWORD</span>
        <input type="password" name="pwd" id="newPassWord" placeholder="Type your Password" /><br>
        <span data-symbol="&#xf190;"></span>
        <span>PASSWROD CHECK</span>
        <input type="password" name="pwd2" id="passWordCheck" placeholder="Type your Password Check" /><br>
        <span id="renew"></span><br>
        <span>EMAIL</span>
        <input class="input100" type="email" name="email" id="userEmail" placeholder="Type your email" /><br>
        <span data-symbol="&#9993;"></span>
        <button type="submit">SIGN UP</button>
        <button type="button">CANCEL</button>
    </form>

</body>
<script>

    let idCheck = 'N'
    let emailCheck = 'N'

    $("#userId").keyup(function() {
        var query = {
            userId : $("#userId").val()
        };

        $.ajax({
            url : "/RoadMap/idCheck.do",
            type : "post",
            data : query,
            success : function(data) {
                if (data == 1) {
                    $(".msg").text("사용하고 있는 아이디입니다.");
                    $(".msg").attr("style", "color:#f00");
                    idCheck = 'N'
                } else {
                    $(".msg").text("사용 가능한 아이디입니다.");
                    $(".msg").attr("style", "color:#00f");
                    //$('#userId').attr("disabled", true);
                    idCheck = 'Y'
                }
            }
        }); // ajax 끝
    });

    $('#passWordCheck').keyup(function() {

        var pw = document.getElementById("newPassWord").value; //비밀번호
        var pw2 = document.getElementById("passWordCheck").value; // 확인 비밀번호

        if (pw != '' && pw2 == '') {
            null;
        } else if (pw != "" || pw2 != "") {
            if (pw == pw2) {
                $("#renew").text("비밀번호가 일치합니다.");
                $("#renew").css("color", "#00f");
                newPwd = 'Y';
            } else {
                $("#renew").text("비밀번호가 일치하지 않습니다.");
                $("#renew").css("color", "#f00");
                newPwd = 'N';
            }
        }

    })

    function check() {
        if (doCheck == 'N') {
            alert("사용중인 아이디입니다.")
            return false;
        }
    }

    //이메일 중복확인
    $("#userEmail").keyup(function() {
        var query = {
            userEmail : $("#userEmail").val()
        };

        $.ajax({
            url : "emailCheck.do",
            type : "post",
            data : query,
            success : function(data) {

                if (data == 1) {
                    $(".msg2").text("사용하고 있는 이메일입니다.");
                    $(".msg2").attr("style", "color:#f00");
                    emailCheck = 'N'
                } else {
                    $(".msg2").text("사용 가능한 이메일입니다.");
                    $(".msg2").attr("style", "color:#00f");
                    emailCheck = 'Y'
                }
            }
        }); // ajax 끝
    });

    function check() {
        if (emailCheck == 'N') {
            alert("입력을 다시 확인해주세요.")
            return false;
        }
    }
</script>
</html>
