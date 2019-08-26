<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="format" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<link href="webjars/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
	<spring:url value="/resources/css/style.css" var="mainCss" />
	<spring:url value="/resources/images/header.png" var="headerImg" />
	<spring:url value="/resources/images/footer.png" var="footerImg" />
	<meta charset="UTF-8">
	<title>Grocery List</title>
	<link rel="stylesheet" href="${mainCss}" />
</head>

<body>
	<div id="page">
		<%@ include file="common/header-common.jspf" %>
		<section>
			<h1>Grocery List</h1>
			<table>
				<tr><th>ID</th><th>Product</th><th>Price</th><th>Bought?</th><th>Buy</th></tr>
				<format:forEach items="${grocerylist}" var="item">
					<tr>
						<td>${item.id}</td>
						<td>${item.product}</td>
						<td>${item.price}</td>
						<td>${item.bought}</td>
						<td><a href="/purchase?id=${item.id}">Purchase</a></td>
					</tr>
				</format:forEach>
			</table>
			<p><a href="/additem">Add a grocery item</a></p>
		</section>
		<%@ include file="common/footer-common.jspf" %>
	</div>
	<script src="webjars/jquery/1.9.1/jquery.min.js"></script>
    <script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>

</html>