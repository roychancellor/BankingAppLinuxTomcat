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
	<title>End of Month</title>
	<link rel="stylesheet" href="${mainCss}" />
</head>

<body>
	<div class="container">
		<%@ include file="common/header-common.jspf" %>
		<section>
			<money:setLocale value="en_US"/>
			<table class="table table-condensed table-striped table-hover">
				<tr class="active">
					<td class="col-md-3"><h1>End of Month for</h1></td>
					<td class="col-md-3"><h1><strong style="color:#522398">${fullname}</strong></h1></td>
					<td class="col-md-3"></td>
					<td class="col-md-3"></td>
				</tr>
			</table>
			<table class="table table-condensed table-striped table-hover">
				<tr class="info">
					<td class="col-md-3"><h3><strong>Fees</strong></h3></td>
					<td class="col-md-2"></td>
					<td class="col-md-3"><h3><strong>Interest</strong></h3></td>
					<td class="col-md-2"></td>
					<td class="col-md-2"></td>
				</tr>
				<tr>
					<td class="col-md-3">Savings service fee:</td>
					<td class="col-md-2"><money:formatNumber value="${savfee}" type="currency" pattern="$#,##0.00;($#,##0.00)" minFractionDigits="2"/></td>
					<td class="col-md-3">Savings interest earned:</td>
					<td class="col-md-2"><money:formatNumber value="${savinterest}" type="currency" pattern="$#,##0.00;($#,##0.00)" minFractionDigits="2"/></td>
					<td class="col-md-2"></td>
				</tr>
				<tr>
					<td class="col-md-3">Cash Advance late fee:</td>
					<td class="col-md-3"><money:formatNumber value="${loanfee}" type="currency" pattern="$#,##0.00;($#,##0.00)" minFractionDigits="2"/></td>
					<td class="col-md-3">Cash advance interest paid:</td>
					<td class="col-md-3"><money:formatNumber value="${loaninterest}" type="currency" pattern="$#,##0.00;($#,##0.00)" minFractionDigits="2"/></td>
				</tr>
			</table>
			<table class="table table-condensed table-striped table-hover">
				<tr class="info">
					<td class="col-md-3"><h3><strong>Ending Balances</strong></h3></td>
					<td class="col-md-2"></td>
					<td class="col-md-2"></td>
					<td class="col-md-5"></td>
				</tr>
				<tr><th class="col-md-2">Checking</th>
				<th class="col-md-2">Saving</th>
				<th class="col-md-4">Cash Advance</th>
				<th class="col-md-4"></th>
				</tr>
				<tr>
					<td class="col-md-2"><money:formatNumber value="${chkbal}" type="currency" pattern="$#,##0.00;($#,##0.00)" minFractionDigits="2"/></td>
					<td class="col-md-2"><money:formatNumber value="${savbal}" type="currency" pattern="$#,##0.00;($#,##0.00)" minFractionDigits="2"/></td>
					<td class="col-md-4">
						<money:formatNumber value="${loanbal}" type="currency" pattern="$#,##0.00;($#,##0.00)" minFractionDigits="2"/>
						(Available credit: <money:formatNumber value="${loanavail}" type="currency" pattern="$#,##0.00;($#,##0.00)" minFractionDigits="2"/>)
					</td>
					<td class="col-md-4"></td>
				</tr>
			</table>
			<p>
				<a style="font-size:1.5em" class="btn btn-success"
					href="${pageContext.request.contextPath}/statements-bank">Transactions</a>
				<a style="font-size:1.5em" class="btn btn-primary"
					href="${pageContext.request.contextPath}/dashboard">Dashboard</a>
			</p>
		</section>
		<%@ include file="common/footer-common.jspf" %>
	</div>
	<script src="webjars/jquery/1.9.1/jquery.min.js"></script>
    <script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>

</html>