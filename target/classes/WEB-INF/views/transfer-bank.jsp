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
	<title>Transfer</title>
	<link rel="stylesheet" href="${mainCss}" />
</head>

<body>
	<div class="container">
		<%@ include file="common/header-common.jspf" %>
		<section>
			<h1>TRANSFER Between Accounts</h1>
			<h3>Welcome ${fullname}!</h3>
			<form action="/transfer-bank" method="POST">
				<p>FROM Account:<br />
					<input type="radio" name="accountfrom" value="chk" checked> Checking (${acctchk})<br />
					<input type="radio" name="accountfrom" value="sav"> Saving (${acctsav})<br />
					<input type="radio" name="accountfrom" value="loan"> Cash Advance (${acctloan})<br />
				</p>
				<p>Amount to transfer:<br /><input type="text" name="amounttransfer"/></p>
				<p>TO Account:<br />
					<input type="radio" name="accountto" value="chk"> Checking (${acctchk})<br />
					<input type="radio" name="accountto" value="sav" checked> Saving (${acctsav})<br />
					<input type="radio" name="accountto" value="loan"> Cash Advance (${acctloan})<br />
				</p>
			<!--  submit button -->
			<p>
				<input class="btn btn-success" type="submit" value="Submit Transfer">
				<a class="btn btn-primary" href="/dashboard">Cancel and Return to Dashboard</a>
			</p>
			</form>
		</section>
		<%@ include file="common/footer-common.jspf" %>
	</div>
	<script src="webjars/jquery/1.9.1/jquery.min.js"></script>
    <script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>

</html>