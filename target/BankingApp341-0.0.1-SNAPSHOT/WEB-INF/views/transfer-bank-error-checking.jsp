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
	<title>Withdraw</title>
	<link rel="stylesheet" href="${mainCss}" />
</head>

<body>
	<div class="container">
		<%@ include file="common/header-common.jspf" %>
		<section>
			<h1>Checking TRANSFER OVERDRAFT Notice</h1>
			<overdraftform:form action="/transfer-bank-error-checking?amount=${reqamount}&toAccount=${toAccount}" method="POST">
				<p>
 					A transfer of
 					<money:formatNumber value="${reqamount}" type="currency" pattern="$#,##0.00;($#,##0.00)" minFractionDigits="2"/>
 					exceeds your available checking balance of
 					<money:formatNumber value="${balance}" type="currency" pattern="$#,##0.00;($#,##0.00)" minFractionDigits="2"/>.<br>
 					If you proceed, your account will receive an overdraft charge of
 					<money:formatNumber value="${overdraft}" type="currency" pattern="$#,##0.00;($#,##0.00)" minFractionDigits="2"/>.<br>
 					Click <strong>Proceed</strong> to proceed with the transfer and receive the charge.<br>
 					Click <strong>Cancel</strong> to cancel and return to the previous page.<br>
					<input class="btn btn-success" type="submit" name="reqamount" value="Proceed">
					<a class="btn btn-primary" href="/transfer-bank">Cancel</a>
				</p>
			</overdraftform:form>
		</section>
		<%@ include file="common/footer-common.jspf" %>
	</div>
	<script src="webjars/jquery/1.9.1/jquery.min.js"></script>
    <script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>

</html>