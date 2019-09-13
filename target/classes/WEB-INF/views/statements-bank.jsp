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
			<!-- Date transactionDate, String accountNumber, double amount, String transactionType -->
			<money:setLocale value="en_US"/>
			<table class="table table-condensed table-striped table-hover">
				<tr class="active">
					<td><h1 style="vertical-align:middle">Transactions for</h1></td>
					<td><h1 style="vertical-align:middle"><strong style="color:#522398">${fullname}</strong></h1></td>
					<td></td>
				</tr>
				<tr class="active">
					<td><h3>Dates: <b>${fromdate}</b></h3></td>
					<td><h3>to <b>${todate}</b></h3></td>
					<td></td>
					<td></td>
				</tr> 
				<tr class="info">
					<td><b>CHECKING</b></td>
					<td>Balance: <b><money:formatNumber value="${chkbal}" type="currency" pattern="$#,##0.00;($#,##0.00)" minFractionDigits="2"/></b></td>
					<td></td>
				</tr>
				<tr><th class="col-md-3">Date-Time</th><th class="col-md-3">Amount</th><th class="col-md-6">Description</th></tr>
				<format:forEach items="${transchk}" var="tran">
					<tr>
						<td>${tran.transactionDate}</td>
						<td><money:formatNumber value="${tran.amount}" type="currency" pattern="$#,##0.00;($#,##0.00)" minFractionDigits="2"/></td>
						<!--  <td>${tran.amount}</td> -->
						<td>${tran.transactionType}</td>
					</tr>
				</format:forEach>
				<tr class="info">
					<td><b>SAVINGS</b></td>
					<td>Balance: <b><money:formatNumber value="${savbal}" type="currency" pattern="$#,##0.00;($#,##0.00)" minFractionDigits="2"/></b></td>
					<td></td>
				</tr>
				<tr><th class="col-md-3">Date-Time</th><th class="col-md-3">Amount</th><th class="col-md-6">Description</th></tr>
				<format:forEach items="${transsav}" var="tran">
					<tr>
						<td>${tran.transactionDate}</td>
						<td><money:formatNumber value="${tran.amount}" type="currency" pattern="$#,##0.00;($#,##0.00)" minFractionDigits="2"/></td>
						<!--  <td>${tran.amount}</td> -->
						<td>${tran.transactionType}</td>
					</tr>
				</format:forEach>
				<tr class="info">
					<td><b>CASH ADVANCE</b></td>
					<td>Balance: <b><money:formatNumber value="${loanbal}" type="currency" pattern="$#,##0.00;($#,##0.00)" minFractionDigits="2"/></b></td>
					<td></td>
				</tr>
				<tr><th class="col-md-3">Date-Time</th><th class="col-md-3">Amount</th><th class="col-md-6">Description</th></tr>
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