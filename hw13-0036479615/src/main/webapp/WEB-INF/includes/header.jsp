<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="hr.fer.zemris.java.hw13.servlets.SetColorServlet"%>
<%@ page session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
	<head>
		<meta charset="utf-8">
	</head>
	<body style="background-color:${(sessionScope.pickedBgCol == null) ? SetColorServlet.WHITE : pickedBgCol}">