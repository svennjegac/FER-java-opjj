<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="hr.fer.zemris.java.hw13.servlets.AppInfoServlet" %>
<%@include file="/WEB-INF/includes/header.jsp"%>

	<h1>Application info</h1>
	<pre>
		App is running:
			${info.days} days,
			${info.hours} hours,
			${info.minutes} minutes,
			${info.seconds} seconds,
			${info.millis} milliseconds
	</pre>
	
	<br>
	<a href="index.jsp">Back to index</a>
	
<%@include file="/WEB-INF/includes/footer.jsp"%>