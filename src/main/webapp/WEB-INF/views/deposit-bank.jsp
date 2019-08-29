<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
	<link href="webjars/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
	<spring:url value="/resources/css/style.css" var="mainCss" />
	<spring:url value="/resources/images/header.jpg" var="headerImg" />
	<spring:url value="/resources/images/footer.jpg" var="footerImg" />
	<meta charset="UTF-8">
	<title>Deposit</title>
	<link rel="stylesheet" href="${mainCss}" />
</head>

<body>
	<div class="container">
		<%@ include file="common/header-common.jspf" %>
		<section>
			<h1>DEPOSIT INTO ACCOUNT ${fullname}, ${email}</h1>
			<form action="/deposit-bank" method="POST">
				<p>Select Account:<br />
					<input type="radio" name="account" value="chk" checked>Checking (${acctchk})<br />
					<input type="radio" name="account" value="sav">Saving (${acctsav})<br />
					<input type="radio" name="account" value="loan">Payment to Cash Advance (${acctloan})<br />
				</p>
				<p>Amount to deposit:<br /><input type="text" name="amountdeposit"/></p>
			<!--  submit button -->
			<p><input class="btn btn-primary" type="submit" value="Submit Deposit"></p>
			</form>
		</section>
		<%@ include file="common/footer-common.jspf" %>
	</div>
	<script src="webjars/jquery/1.9.1/jquery.min.js"></script>
    <script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>

</html>