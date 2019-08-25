<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
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
</body>

</html>