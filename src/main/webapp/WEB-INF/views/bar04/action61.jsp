<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>bar/action61</title>
</head>
<body>
	<form:form modelAttribute="person" action="action62">
		<p>
			<label for="name">产品类型：</label>
			<form:select path="education">
			   <form:option value="">--请选择--</form:option>
			   <form:options items="${productTypes}" itemLabel="name" itemValue="id"/>
			</form:select>
		</p>
		<p>
			<button>提交</button>
		</p>
	</form:form>
</body>
</html>