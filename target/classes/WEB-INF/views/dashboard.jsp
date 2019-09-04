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
	<title>Customer Dashboard</title>
	<link rel="stylesheet" href="${mainCss}" />
</head>

<body>
	<div class="container">
		<%@ include file="common/header-common.jspf" %>
		<section>
			<h1>Customer Dashboard</h1>
			<h3>Welcome ${fullname}!</h3>
			<h5>Current Account Balances</h5>
			<table class="table table-striped">
				<tr><th>Checking</th><th>Saving</th><th>Cash Advance</th></tr>
				<tr>
					<money:setLocale value="en_US"/>
					<td class="content"><money:formatNumber value="${chkbal}" type="currency" pattern="$#,##0.00;($#,##0.00)" minFractionDigits="2"/></td>
					<td class="content"><money:formatNumber value="${savbal}" type="currency" pattern="$#,##0.00;($#,##0.00)" minFractionDigits="2"/></td>
					<td class="content"><money:formatNumber value="${loanbal}" type="currency" pattern="$#,##0.00;($#,##0.00)" minFractionDigits="2"/></td>
				</tr>
			</table>
			<p><a class="btn btn-success" href="/statements-bank">Reports</a></p>
		</section>
		<%@ include file="common/footer-common.jspf" %>
	</div>
	<script src="webjars/jquery/1.9.1/jquery.min.js"></script>
    <script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>

</html>