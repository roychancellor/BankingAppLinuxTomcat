<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="login" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="format" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<link href="webjars/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
	<spring:url value="/resources/css/style.css" var="mainCss" />
	<spring:url value="/resources/images/header.jpg" var="headerImg" />
	<spring:url value="/resources/images/footer.jpg" var="footerImg" />
	<meta charset="UTF-8">
	<title>Delete Account</title>
	<link rel="stylesheet" href="${mainCss}" />
</head>

<body>
	<div class="container">
		<%@ include file="common/header-common-login.jspf" %>
		<section>
			<h1>Enter Your Password to Begin Deletion Process</h1>
			<p class="error">${errorMessage}</p>
			<login:form modelAttribute="passwordform" action="/delete-login" method="POST">
				<div class="form-group row">
					<label class="col-lg-1 col-form-label">Username:</label>
					<div class="col-lg-3">
						<p class="form-control-static">${username}</p>
					</div>
				</div>
				<div class="form-group row">
					<login:label path="password" class="col-lg-1 col-form-label">Password:</login:label>
					<div class="col-lg-3">
						<login:input type="password" class="form-control" placeholder="Enter password" path="password" />
						<login:errors path="password" cssClass="error" />
					</div>
				</div>
				<div class="form-group row">
					<div class="col-sm-3">
						<button class="btn btn-primary" type="submit">Click to Delete</button>
					</div>
				</div>
				<div class="form-group row">
					<div class="col-sm-4">
						<a class="btn btn-success btn-lg" href="/customer-settings">Cancel Without Deleting</a>
					</div>
				</div>
			</login:form>
		</section>
		<%@ include file="common/footer-common.jspf" %>
	</div>
	<script src="webjars/jquery/1.9.1/jquery.min.js"></script>
    <script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>

</html>