<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>bar/action41</title>
</head>
<body>
	<form:form modelAttribute="person" action="action42">
		<p>
			<label for="name">产品类型：</label>
			<form:select size="3" multiple="multiple" path="education" items="${productTypes}"  itemLabel="name"  itemValue="id"></form:select>
		</p>
		<p>
		<button>提交</button>
		</p>
	</form:form>
</body>
</html>