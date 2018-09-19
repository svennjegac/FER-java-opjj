<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/includes/header.jsp"%>

	<a href="colors.jsp">Background color chooser</a><br>
	<a href="trigonometric?a=0&b=90">Trigonometric with default values (a = 0, b = 90)</a><br>
	<a href="report.jsp">Show OS usage</a><br>
	<a href="appinfo">App info</a><br>
	<a href="powers?a=1&b=100&n=3" download>Powers (a = 1, b = 100, n = 3)</a><br>
	<a href="stories/funny.jsp">Funny story</a><br>
	<a href="glasanje">Glasanje za band</a>
	<form action="trigonometric" method="GET">
		Početni kut:<br>
		<input type="number" name="a" min="0" max="360" step="1" value="0"><br>
		Završni kut<br>
		<input type="number" name="b" min="0" max="360" step="1" value="360"><br>
		<input type="submit" value="Tabeliraj"> <input type="reset" value="Reset">
	</form>

<%@include file="/WEB-INF/includes/footer.jsp"%>