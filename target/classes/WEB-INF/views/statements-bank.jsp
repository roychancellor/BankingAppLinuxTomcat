<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="format" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="money" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
			<h1>Account Statements</h1>
			<h3>Statement for ${fullname}</h3>
			<!-- Date transactionDate, String accountNumber, double amount, String transactionType -->
			<money:setLocale value="en_US"/>
			<table class="table table-striped">
				<tr><td><b>CHECKING</b> (current balance = <money:formatNumber value="${chkbal}" type="currency" pattern="$#,##0.00;($#,##0.00)" minFractionDigits="2"/>)</td><td></td><td></td></tr>
				<tr><th>Date</th><th>Amount</th><th>Description</th></tr>
				<format:forEach items="${transchk}" var="tran">
					<tr>
						<td>${tran.transactionDate}</td>
						<td><money:formatNumber value="${tran.amount}" type="currency" pattern="$#,##0.00;($#,##0.00)" minFractionDigits="2"/></td>
						<!--  <td>${tran.amount}</td> -->
						<td>${tran.transactionType}</td>
					</tr>
				</format:forEach>
				<tr><td><b>SAVINGS</b> (current balance = <money:formatNumber value="${savbal}" type="currency" pattern="$#,##0.00;($#,##0.00)" minFractionDigits="2"/>)</td><td></td><td></td></tr>
				<tr><th>Date</th><th>Amount</th><th>Description</th></tr>
				<format:forEach items="${transsav}" var="tran">
					<tr>
						<td>${tran.transactionDate}</td>
						<td><money:formatNumber value="${tran.amount}" type="currency" pattern="$#,##0.00;($#,##0.00)" minFractionDigits="2"/></td>
						<!--  <td>${tran.amount}</td> -->
						<td>${tran.transactionType}</td>
					</tr>
				</format:forEach>
				<tr><td><b>CASH ADVANCE</b> (current balance = <money:formatNumber value="${loanbal}" type="currency" pattern="$#,##0.00;($#,##0.00)" minFractionDigits="2"/>)</td><td></td><td></td></tr>
				<tr><th>Date</th><th>Amount</th><th>Description</th></tr>
				<format:forEach items="${transloan}" var="tran">
					<tr>
						<td>${tran.transactionDate}</td>
						<td><money:formatNumber value="${tran.amount}" type="currency" pattern="$#,##0.00;($#,##0.00)" minFractionDigits="2"/></td>
						<!--  <td>${tran.amount}</td> -->
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