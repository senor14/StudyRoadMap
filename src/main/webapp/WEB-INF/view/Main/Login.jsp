<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>Reminder Login</title>
	<meta charset="UTF-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<style>

	</style>
</head>

<body>

	<form action="/Reminder/ReminderLoginProc.do" method="post">
		<span>ID</span>
		<input type="text" name="id" placeholder="Type your ID" /><br>
		<span>Password</span>
		<input type="password" name="pwd" placeholder="Type your password"/><br>
		<a href="/Reminder/ForgotPassWord.do"> Forgot password? </a><br>
		<button type="submit">Login</button><br>
		<a href="/RoadMap/SignUp.do"> Sign Up </a>
	</form>

</body>
<script>


</script>
</html>