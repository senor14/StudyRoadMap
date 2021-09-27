<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	String url = (String)request.getAttribute("url");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript">
location.href = "<%=url %>"
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>redirectNArt</title>
</head>
<body>
</body>
</html>