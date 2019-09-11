<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="confirmcustomer" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="format" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<link href="webjars/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
	<spring:url value="/resources/css/style.css" var="mainCss" />
	<spring:url value="/resources/images/header.jpg" var="headerImg" />
	<spring:url value="/resources/images/footer.jpg" var="footerImg" />
	<meta charset="UTF-8">
	<title>Confirm Customer</title>
	<link rel="stylesheet" href="${mainCss}" />
</head>

<body>
	<div class="container">
		<%@ include file="common/header-common.jspf" %>
		<section>
			<h1>Confirm NEW Customer Information</h1>
			<h5>
				Please confirm your updated information. If correct, click <strong>Submit Information</strong><br>
				If anything is incorrect, click <strong>Cancel and Fix</strong>
			</h5>
			<confirmcustomer:form modelAttribute="customer" action="/update-customer" method="POST">
				<div class="form-row">
					<div class="form-group col-md-6">
					First name: ${customer.firstName}
					</div>
					<div class="form-group col-md-6">
					Last name: ${customer.lastName}
					</div>
				</div>
				<div class="form-row">
					<div class="form-group col-md-6">
					Username: ${customer.username}
					</div>
					<div class="form-group col-md-6">
					NEW password: ****************
					</div>
				</div>
				<div class="form-row">
					<div class="form-group col-md-6">
					NEW E-mail Address: ${customer.emailAddress}
					</div>
					<div class="form-group col-md-6">
					NEW Phone number: ${customer.phoneNumber}
					</div>
				</div>
				<input class="btn btn-success" type="submit" name="SubmitBtn" value="Submit Information">
				<a class="btn btn-primary" href="/customer-update">Cancel and Fix</a>
			</confirmcustomer:form>
		</section>
		<%@ include file="common/footer-common.jspf" %>
	</div>
	<script src="webjars/jquery/1.9.1/jquery.min.js"></script>
    <script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>

</html>