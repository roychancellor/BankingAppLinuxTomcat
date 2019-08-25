<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
	<spring:url value="/resources/css/style.css" var="mainCss" />
	<spring:url value="/resources/images/header.png" var="headerImg" />
	<spring:url value="/resources/images/footer.png" var="footerImg" />
	<meta charset="UTF-8">
	<title>Login</title>
	<link rel="stylesheet" href="${mainCss}" />
</head>

<body>
	<div id="page">
		<%@ include file="common/header-common.jspf" %>
		<section>
			<h1>Login</h1>
			<p style="color:#f00">${errormessage}</p>
			<form action="/login" method="POST">
				<p>Username:<br /><input type="text" name="uname"/></p>
				<p>Password:<br /><input type="password" name="pass"/></p>
				<p><input type="submit" /></p>
			</form>
		</section>
		<%@ include file="common/footer-common.jspf" %>
	</div>
</body>

</html>