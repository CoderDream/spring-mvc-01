<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>bar/action21</title>
</head>
<body>
	<form:form modelAttribute="person" action="action22">
		<p>
			<label for="name">婚否：</label>
			<form:checkbox path="isMarried" />
		</p>
		<p>
			<label for="name">爱好：</label>
			<form:checkbox path="hobbies" value="读书"/>读书
			<form:checkbox path="hobbies" value="上网"/>上网
			<form:checkbox path="hobbies" value="电影"/>电影
		</p>
		<p>
			<label for="name">毕业：</label>
			<form:checkbox path="education" value="本科"/>大学本科
		</p>
		<p>
		<button>提交</button>
		</p>
	</form:form>
</body>
</html>