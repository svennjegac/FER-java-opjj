<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
	<head>
		<meta charset="utf-8">
	</head>
	<body>
	<h1 align="center">Blog</h1>
	<p align="right">	
		<c:choose>
			<c:when test="${current_user_id == null}">
				<c:out value="You are not logged in."></c:out>
			</c:when>
			<c:otherwise>
				<c:out value="Logged in as: ${current_user_nick}"></c:out> | 
				<a href="/blog/servleti/logout">Log Out</a>
			</c:otherwise>
		</c:choose>
	</p>