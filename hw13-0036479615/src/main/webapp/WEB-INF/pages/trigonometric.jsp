<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@include file="/WEB-INF/includes/header.jsp"%>

	<table border="1">
		<tr>
			<th>Value</th>
			<th>Sinus</th>
			<th>Cosinus</th>
		</tr>
	
		<c:forEach var="trig" items="${trigonometricResults}">
			<tr>
				<td align="right"><fmt:formatNumber maxFractionDigits="2">${trig.value}</fmt:formatNumber></td>
				<td align="right"><fmt:formatNumber maxFractionDigits="2">${trig.sin}</fmt:formatNumber></td>
				<td align="right"><fmt:formatNumber maxFractionDigits="2">${trig.cos}</fmt:formatNumber></td>
			</tr>
		</c:forEach>
	
	</table>
	
	<br>
	
	<a href="/webapp2/index.jsp">Back to index.</a>

<%@include file="/WEB-INF/includes/footer.jsp"%>