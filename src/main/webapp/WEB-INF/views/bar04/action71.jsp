<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>bar/action71</title>
</head>
<body>
	<form:form modelAttribute="person" action="action72">
		<p>
			<form:hidden path="education" />
			<input type="hidden" value="1" name="id">
		</p>
		<p>
			<button>提交</button>
		</p>
	</form:form>
</body>
</html>