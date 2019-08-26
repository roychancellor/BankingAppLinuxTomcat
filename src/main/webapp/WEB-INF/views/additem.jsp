<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="addform" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
	<link href="webjars/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
	<spring:url value="/resources/css/style.css" var="mainCss" />
	<spring:url value="/resources/images/header.png" var="headerImg" />
	<spring:url value="/resources/images/footer.png" var="footerImg" />
	<meta charset="UTF-8">
	<title>Add an Item</title>
	<link rel="stylesheet" href="${mainCss}" />
</head>

<body>
	<div id="page">
		<%@ include file="common/header-common.jspf" %>
		<section>
			<h1>Add Item to Grocery List</h1>
			<p class="error">${message}</p>
			<addform:form modelAttribute="grocery" method="POST">
			<addform:hidden path="id"/>
			<addform:hidden path="user"/>
				<p><addform:label path="product">Product Name:</addform:label><br /><addform:input type="text" path="product" />
				<addform:errors path="product" cssClass="error" /></p>
				<p><addform:label path="price">Product Price:</addform:label><br /><addform:input type="text" path="price" />
				<addform:errors path="price" cssClass="error" /></p>
				<p><input type="submit" name="add"></p>
			</addform:form>
		</section>
		<%@ include file="common/footer-common.jspf" %>
	</div>
	<script src="webjars/jquery/1.9.1/jquery.min.js"></script>
    <script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>

</html>