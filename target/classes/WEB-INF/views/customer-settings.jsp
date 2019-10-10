<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="update" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="format" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<link href="webjars/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
	<spring:url value="/resources/css/style.css" var="mainCss" />
	<spring:url value="/resources/images/header.jpg" var="headerImg" />
	<spring:url value="/resources/images/footer.jpg" var="footerImg" />
	<meta charset="UTF-8">
	<title>Settings</title>
	<link rel="stylesheet" href="${mainCss}" />
</head>

<body>
	<div class="container">
		<%@ include file="common/header-common-login.jspf" %>
		<section>
			<h1>Customer Settings for <strong style="color:#522398">${fullname}</strong></h1>
			<table class="table table-hover">
			  <thead>
			    <tr>
			      <th scope="col">Action</th>
			      <th scope="col">Description</th>
			    </tr>
			  </thead>
			  <tbody>
			    <tr class="info">
			      <td><a class="btn btn-info btn-lg"
			      		href="${pageContext.request.contextPath}/customer-update">Update Your Information</a></td>
			      <td>Click this button to update e-mail address, phone number, and/or password.
			      You will have the option to cancel at any time. Requires password to proceed.</td>
			    </tr>
			    <tr class="danger">
			      <td><a class="btn btn-danger btn-lg"
			      		href="${pageContext.request.contextPath}/customer-delete">Close Your Account</a></td>
			      <td>Click this button to close your account and delete your information from the bank. 
			      You will have the option to cancel at any time. Requires password to proceed.</td>
			    </tr>
			    <tr class="primary">
			      <td><a class="btn btn-primary btn-lg"
			      		href="${pageContext.request.contextPath}/dashboard">Cancel</a></td>
			      <td>Click this button to cancel and return to the dashboard.</td>
			    </tr>
			    <tr class="warning">
			      <td><a class="btn btn-warning btn-lg"
			      		href="${pageContext.request.contextPath}/logout">Logout</a></td>
			      <td>Click this button to log out of your account</td>
			    </tr>
			  </tbody>
			</table>
		</section>
		<%@ include file="common/footer-common.jspf" %>
	</div>
	<script src="webjars/jquery/1.9.1/jquery.min.js"></script>
    <script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>

</html>