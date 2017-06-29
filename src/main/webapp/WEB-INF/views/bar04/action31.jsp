<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>bar/action31</title>
</head>
<body>
	<form:form modelAttribute="person" action="action32">
		<p>
			<label for="name">学历：</label>
			<form:radiobutton path="education" value="专科"/>专科
			<form:radiobutton path="education" value="本科"/>本科
			<form:radiobutton path="education" value="研究生"/>研究生
		</p>
		<p>
		   <label>密码：</label>
		   <form:password path="education" showPassword="false"/>
		</p>
		<p>
		<button>提交</button>
		</p>
	</form:form>
</body>
</html>