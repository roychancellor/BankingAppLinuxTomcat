<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="overdraftform" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="money" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
	<link href="webjars/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
	<spring:url value="/resources/css/style.css" var="mainCss" />
	<spring:url value="/resources/images/header.jpg" var="headerImg" />
	<spring:url value="/resources/images/footer.jpg" var="footerImg" />
	<meta charset="UTF-8">
	<meta http-equiv="refresh" content="5; url=login">
	<title>Success!</title>
	<link rel="stylesheet" href="${mainCss}" />
</head>

<body>
	<div class="container">
		<%@ include file="common/header-common.jspf" %>
		<section>
		<div class="alert alert-success" role="alert">
  			<h2 class="alert-heading">Success!</h2>
  			<h3>You are now a GCU Bank customer. Welcome!</h3>
  			<hr>
  			<h4 class="mb=0">Redirecting to the Login page...</h4>
		</div>
		</section>
		<%@ include file="common/footer-common.jspf" %>
	</div>
	<script src="webjars/jquery/1.9.1/jquery.min.js"></script>
    <script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
  	<script src="/resources/webstatic/successfade.js"></script>
</body>

</html>