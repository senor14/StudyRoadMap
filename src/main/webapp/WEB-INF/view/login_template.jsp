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
    <link rel="shortcut icon" href="#">
    <title>Login Template</title>
</head>

<body translate="no">
<div class="form-structor">
    <div class="signup">
        <h2 class="form-title" id="signup"><span>or</span>Sign up</h2>
        <div class="form-holder">
            <input type="text" class="input" placeholder="Name">
            <input type="email" class="input" placeholder="Email">
            <input type="password" class="input" placeholder="Password">
        </div>
        <button class="submit-btn">Sign up</button>
    </div>
    <div class="login slide-up">
        <div class="center">
            <h2 class="form-title" id="login"><span>or</span>Log in</h2>
            <div class="form-holder">
                <input type="text" class="input" placeholder="Id">
                <input type="password" class="input" placeholder="Password">
            </div>
            <button class="submit-btn">Log in</button>
        </div>
    </div>
</div>


</body>


<script src="https://cpwebassets.codepen.io/assets/common/stopExecutionOnTimeout-1b93190375e9ccc259df3a57c1abc0e64599724ae30d7ea4c6877eb615f89387.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/login_main.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery-migrate-3.0.1.min.js"></script>
</html>