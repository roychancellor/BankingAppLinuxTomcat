<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="overdraftform" uri="http://www.springframework.org/tags/form"%>
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
			<h1>Checking OVERDRAFT Notice</h1>
			<overdraftform:form modelAttribute="amount" action="/withdraw-bank" method="POST">
<%-- 				<p>Select Account:<br />
					<input type="radio" name="account" value="chk" checked> Checking (${acctchk})<br />
					<input type="radio" name="account" value="sav"> Saving (${acctsav})<br />
					<input type="radio" name="account" value="loan"> From Cash Advance (${acctloan})<br />
				</p>
				<p><overdraftform:label path="amount">Amount to withdraw:</overdraftform:label>
				<br />
				<overdraftform:input type="text" path="amount"/>
				<overdraftform:errors path="amount" cssClass="error" /></p>				
				<p class="error">${errormessage}</p>
 --%>				<p>
 					Your amount of ${amount} exceeds your available balance of ${balance}.<br>
 					If you proceed, your account will receive an overdraft charge of ${overdraft}.<br>
 					Click <strong>Proceed</strong> to proceed with the withdrawal and receive the charge.<br>
 					Click <strong>Cancel</strong> to cancel the transaction and return to the withdrawal page.<br>
					<input class="btn btn-success" type="submit" value="Proceed with Withdrawal">
					<a class="btn btn-primary" href="/withdraw-bank">Cancel and Return to Withdrawal Page</a>
				</p>
			</overdraftform:form>
		</section>
		<%@ include file="common/footer-common.jspf" %>
	</div>
	<script src="webjars/jquery/1.9.1/jquery.min.js"></script>
    <script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>

</html>