<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JSTL View</title>
</head>
<body>
	<h2>${msg}</h2>
	<ul>
		<c:forEach items="${users}" var="user">
			<li>${user}</li>
		</c:forEach>
	</ul>
</body>
</html>