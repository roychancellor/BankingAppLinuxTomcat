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
	<title>Add a Student</title>
	<link rel="stylesheet" href="${mainCss}" />
</head>

<body>
	<div id="page">
		<header><img src="${headerImg}" /></header>
		<section>
			<h1>Add Student to List</h1>
			<form action="/addstudent" method="POST">
				<p>Last Name:<br /><input type="text" name="lastname" /></p>
				<p>First Name:<br /><input type="text" name="firstname" /></p>
				<p><input type="submit" name="add"></p>
			</form>
		</section>
		<footer><img src="${footerImg}" /></footer>
	</div>
	<script src="webjars/jquery/1.9.1/jquery.min.js"></script>
    <script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>

</html>