<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="format" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<link href="webjars/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
	<spring:url value="/resources/css/style.css" var="mainCss" />
	<spring:url value="/resources/images/header.jpg" var="headerImg" />
	<spring:url value="/resources/images/footer.jpg" var="footerImg" />
	<meta charset="UTF-8">
	<title>Student List</title>
	<link rel="stylesheet" href="${mainCss}" />
</head>

<body>
	<div id="page">
		<header><img src="${headerImg}" /></header>
		<section>
			<h1>Student List (${username})</h1>
			<table>
				<tr><th>ID</th> <th>Last Name</th> <th>First Name</th> <th>Enrolled?</th> <th>Actions</th></tr>
				<!-- MUST BE CALLED items -->
				<format:forEach items="${stulist}" var="student">
					<tr>
						<td>${student.id}</td>
						<td>${student.lastname}</td>
						<td>${student.firstname}</td>
						<td>${student.enrolled}</td>
						<td><a href="/enroll?id=${student.id}">Enroll</a><a href="/delete?id=${student.id}">Delete</a></td>
					</tr>
				</format:forEach>
			</table>
			<p><a href="/addstudent">Add a student</a> <a href="/login">Log out</a></p>
		</section>
		<footer><img src="${footerImg}" /></footer>
	</div>
	<script src="webjars/jquery/1.9.1/jquery.min.js"></script>
    <script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>

</html>