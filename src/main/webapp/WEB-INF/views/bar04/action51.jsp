<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>bar/action51</title>
</head>
<body>
	<form:form modelAttribute="person" action="action52">
		<p>
			<label for="name">学历：</label>
			<form:select path="education">
				<form:option value="" >--请选择--</form:option>
				<form:option value="大专">大专</form:option>
				<form:option value="本科">本科</form:option>
				<form:option value="研究生">研究生</form:option>
			</form:select>
		</p>
		<p>
			<button>提交</button>
		</p>
	</form:form>
</body>
</html>