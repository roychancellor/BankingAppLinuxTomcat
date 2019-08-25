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
			<h1>About</h1>
			<p>This is the coolest grocery list site ever. It is built with HTML and CSS.
		</section>
		<%@ include file="common/footer-common.jspf" %>
	</div>
</body>

</html>