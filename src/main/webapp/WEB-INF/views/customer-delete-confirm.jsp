<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="deleteform" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="money" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
	<link href="webjars/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
	<spring:url value="/resources/css/style.css" var="mainCss" />
	<spring:url value="/resources/images/header.jpg" var="headerImg" />
	<spring:url value="/resources/images/footer.jpg" var="footerImg" />
	<meta charset="UTF-8">
	<title>Confirm Delete</title>
	<link rel="stylesheet" href="${mainCss}" />
</head>

<body>
	<div class="container">
		<%@ include file="common/header-common.jspf" %>
		<section>
			<h1 style="color:red">!!! Confirm Account Deletion !!!</h1>
			<deleteform:form action="${pageContext.request.contextPath}/delete-customer" method="GET">
				<h4>
 					You are about to PERMANENTLY delete ALL of your accounts and customer information.<br>
 					<strong style="color:red">This can not be undone!</strong><br>
 					Click <strong>Delete</strong> to permanently delete your account.<br>
 					Click <strong>Cancel</strong> to cancel and keep your account open.<br>
 					<br>
					<input class="btn btn-danger" type="submit" name="deleteaccount" value="Delete">
					<a class="btn btn-primary" href="${pageContext.request.contextPath}/customer-settings">Cancel</a>
				</h4>
			</deleteform:form>
		</section>
		<%@ include file="common/footer-common.jspf" %>
	</div>
	<script src="webjars/jquery/1.9.1/jquery.min.js"></script>
    <script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>

</html>