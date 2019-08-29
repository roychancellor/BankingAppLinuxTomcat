<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="format" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<link href="webjars/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
	<spring:url value="/resources/css/style.css" var="mainCss" />
	<spring:url value="/resources/images/header.jpg" var="headerImg" />
	<spring:url value="/resources/images/footer.jpg" var="footerImg" />
	<meta charset="UTF-8">
	<title>Customer Statements</title>
	<link rel="stylesheet" href="${mainCss}" />
</head>

<body>
	<div class="container">
		<%@ include file="common/header-common.jspf" %>
		<section>
			<h1>Customer Statements: ${fullname}, ${email}</h1>
			<!-- Date transactionDate, String accountNumber, double amount, String transactionType -->
			<table class="table table-striped">
				<tr><th>Date</th><th>Amount</th><th>Description</th></tr>
				<format:forEach items="${transchk}" var="tran">
					<tr>
						<td>${tran.transactionDate}</td>
						<td>${tran.amount}</td>
						<td>${tran.transactionType}</td>
					</tr>
				</format:forEach>
				<tr><th>Date</th><th>Amount</th><th>Description</th></tr>
				<format:forEach items="${transsav}" var="tran">
					<tr>
						<td>${tran.transactionDate}</td>
						<td>${tran.amount}</td>
						<td>${tran.transactionType}</td>
					</tr>
				</format:forEach>
				<tr><th>Date</th><th>Amount</th><th>Description</th></tr>
				<format:forEach items="${transloan}" var="tran">
					<tr>
						<td>${tran.transactionDate}</td>
						<td>${tran.amount}</td>
						<td>${tran.transactionType}</td>
					</tr>
				</format:forEach>
			</table>
			<p><a class="btn btn-success" href="/dashboard">Dashboard</a></p>
		</section>
		<%@ include file="common/footer-common.jspf" %>
	</div>
	<script src="webjars/jquery/1.9.1/jquery.min.js"></script>
    <script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>

</html>