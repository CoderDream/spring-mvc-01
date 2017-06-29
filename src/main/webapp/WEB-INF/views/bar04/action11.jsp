<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>bar/action11</title>
</head>
<body>
	<form:form modelAttribute="product">
		<p>
			<label for="name">名称：</label>
			<form:input path="name" cssClass="textCss" cssStyle="color:blue"
				a="b" htmlEscape="false" onclick="alert('Hello')" />
		</p>
		<p>
			<label for="price">价格：</label>
			<form:input path="price" />
		</p>
	</form:form>
</body>
</html>