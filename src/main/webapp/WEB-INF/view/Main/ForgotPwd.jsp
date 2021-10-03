<%--
  User: 김창영
  Date: 2021-10-03
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>RoadMap</title>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <style>

    </style>
</head>

<body>

<form action="/RoadMap/ReMakePW" method="post">
    <span>EMAIL</span>
    <input type="text" name="userEmail" placeholder="Type your EMAIL" /><br>
    <span>ID</span>
    <input type="text" name="userId" placeholder="Type your ID" /><br>
    <button type="submit">확인</button>
    <button type="button" onclick="move_main()">취소</button>
</form>

</body>
<script>

    function move_main() {
        console.log("move_main 실행")
        location.href = "/RoadMap/Login";
    }

</script>
</html>