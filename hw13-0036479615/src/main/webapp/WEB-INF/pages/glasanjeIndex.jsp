<%@ page import="hr.fer.zemris.java.hw13.glasanje.Util"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/includes/header.jsp"%>

	<h1>Glasajte za najdraži band.</h1>
	
	<% if (request.getAttribute(Util.ERROR) != null) { %>
		
		<p>${error}</p>
		
	<% } else { %>
	
		<ol>
  			<c:forEach var="nominee" items="${list}">
  				<li><a href="glasanje-glasaj?id=${nominee.id}">${nominee.bandName}</a></li>
  			</c:forEach>
		</ol>
	
	<% } %>
	
	<br>
	<a href="index.jsp">Back to index</a>

<%@ include file="/WEB-INF/includes/footer.jsp"%>