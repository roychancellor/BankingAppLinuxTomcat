<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="transform" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="money" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
			<h1>Welcome ${fullname}!</h1>
			<h2><strong>TRANSFER Between Accounts</strong></h2>
			<transform:form modelAttribute="amount" action="${pageContext.request.contextPath}/transfer-bank" method="POST">
				<div class="radioform">
					<p><strong>FROM Account:</strong><br />
						<input type="radio" name="fromaccount" value="chk" checked> Checking (${acctchk})
							<strong>Available:
							<money:formatNumber value=" ${chkbal}" type="currency" pattern="$#,##0.00;($#,##0.00)" minFractionDigits="2"/>
							</strong>
							<br />
						<input type="radio" name="fromaccount" value="sav"> Saving (${acctsav})
							<strong>Available:
							<money:formatNumber value=" ${savbal}" type="currency" pattern="$#,##0.00;($#,##0.00)" minFractionDigits="2"/>
							</strong>
							<br />
						<input type="radio" name="fromaccount" value="loan"> Cash Advance (${acctloan})
							<strong>Available:
							<money:formatNumber value=" ${loanavail}" type="currency" pattern="$#,##0.00;($#,##0.00)" minFractionDigits="2"/>
							</strong>
							<br />
					</p>
				</div>
				<div class="amountinput">
					<p><transform:label path="amount">Amount to transfer:</transform:label>
					<br />
					<transform:input type="text" path="amount"/><a class="error">${errormessage}</a>
					<transform:errors path="amount" cssClass="error" /></p>	
				</div>			
				<div class="radioform">
					<p><strong>TO Account:</strong><br />
						<input type="radio" name="toaccount" value="chk"> Checking (${acctchk})
							<br />
						<input type="radio" name="toaccount" value="sav" checked> Saving (${acctsav})
							<br />
						<input type="radio" name="toaccount" value="loan"> Cash Advance (${acctloan})
							<strong>Outstanding balance:
							<money:formatNumber value=" ${loanbal}" type="currency" pattern="$#,##0.00;($#,##0.00)" minFractionDigits="2"/>
							</strong>
							<br />
					</p>
				</div>
				<p>
					<input class="btn btn-success" type="submit" value="Submit Transfer">
					<a class="btn btn-primary" href="${pageContext.request.contextPath}/dashboard">Cancel and Return to Dashboard</a>
				</p>
			</transform:form>
		</section>
		<%@ include file="common/footer-common.jspf" %>
	</div>
	<script src="webjars/jquery/1.9.1/jquery.min.js"></script>
    <script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>

</html>