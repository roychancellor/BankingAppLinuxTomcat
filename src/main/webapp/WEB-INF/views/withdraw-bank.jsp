<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="depform" uri="http://www.springframework.org/tags/form"%>
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
			<h1>Welcome ${fullname}!</h1>
			<h2><strong>WITHDRAW From Account</strong></h2>
			<depform:form modelAttribute="amount" action="${pageContext.request.contextPath}/withdraw-bank" method="POST">
				<div class="radioform">
					<p><strong>Select Account:</strong><br />
						<input type="radio" name="account" value="chk" checked> Checking (${acctchk})
							<strong>Available:
							<money:formatNumber value=" ${chkbal}" type="currency" pattern="$#,##0.00;($#,##0.00)" minFractionDigits="2"/>
							</strong>
							<br />
							<br />
						<input type="radio" name="account" value="sav"> Saving (${acctsav})
							<strong>Available:
							<money:formatNumber value=" ${savbal}" type="currency" pattern="$#,##0.00;($#,##0.00)" minFractionDigits="2"/>
							</strong>
							<br />
							<br />
						<input type="radio" name="account" value="loan"> From Cash Advance (${acctloan})
							<strong>Available:
							<money:formatNumber value=" ${loanavail}" type="currency" pattern="$#,##0.00;($#,##0.00)" minFractionDigits="2"/>
							</strong>
							<br />
							<br />
					</p>
				</div>
				<div class="amountinput">
					<p><depform:label path="amount">Amount to withdraw:</depform:label>
					<br />
					<depform:input type="text" path="amount"/><a class="error">${errormessage}</a>
					<depform:errors path="amount" cssClass="error" /></p>	
				</div>			
				<p>
					<input class="btn btn-success" type="submit" value="Submit Withdrawal">
					<a class="btn btn-primary"
						href="${pageContext.request.contextPath}/dashboard">Cancel and Return to Dashboard</a>
				</p>
			</depform:form>
		</section>
		<%@ include file="common/footer-common.jspf" %>
	</div>
	<script src="webjars/jquery/1.9.1/jquery.min.js"></script>
    <script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>

</html>