<%@ page import="hr.fer.zemris.java.hw13.glasanje.Util"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/includes/header.jsp"%>

	<h1>Glasajte za najdraži band.</h1>
	
	<% if (request.getAttribute(Util.ERROR) != null) { %>
		
		<p>${error}</p>
		
	<% } else { %>
	
		<!-- Table of results -->
		<table border="1">
			<tr>
				<th>Band</th><th>Broj glasova</th>
			</tr>
			<c:forEach var="nominee" items="${list}">
				<tr>
					<td>${nominee.bandName}</td><td>${nominee.votes}</td>
				</tr>
			</c:forEach>
		</table>
		
		<!-- Piechart -->
		<h2>Grafički prikaz rezultata</h2>
		<img src="/webapp2/glasanje-grafika" alt="Nema podataka o glasanju">
		
		<!-- XLS -->
		<h2>Rezultati u XLS formatu</h2>
		<p>Rezultati u XLS formatu dostupni su <a href="/webapp2/glasanje-xls" download>ovdje</a></p>
		
		<!-- Winners -->
		<h2>Pobjednici natjecanja:</h2>
		<ul>
			<c:forEach var="nominee" items="${winners}">
				<li><a href="${nominee.songLink}">${nominee.bandName}</a></li>
			</c:forEach>
		</ul>
	
	<% } %>
	
	<br>
	<a href="/webapp2/glasanje">Odi na glasanje</a><br>
	<a href="index.jsp">Vrati se na index</a>

<%@ include file="/WEB-INF/includes/footer.jsp"%>