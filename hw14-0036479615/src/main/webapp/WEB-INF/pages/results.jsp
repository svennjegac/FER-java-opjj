<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/includes/header.jsp"%>

	<h1>${poll.title}</h1>
	<h2>Rezultati glasanja</h2>
	
	<hr>
	<!-- Tablica rezultata -->
	<table border="1px">
		<tr>
			<th>Natjecatelj</th><th>Broj glasova</th>
		</tr>
		
		<c:forEach var="pollOption" items="${pollOptions}">
			<tr>
				<td>${pollOption.name}</td><td>${pollOption.votes}</td>
			</tr>
		</c:forEach>
	</table>
	
	<!-- Piechart -->
	<h2>Grafički prikaz rezultata</h2>
	<img src="glasanje-grafika?pollID=${poll.id}" alt="Nema podataka o glasanju">
	
	<!-- XLS -->
	<h2>Rezultati u XLS formatu</h2>
	<p>Rezultati u XLS formatu dostupni su <a href="glasanje-xls?pollID=${poll.id}" download>ovdje</a></p>
	
	<!-- Winners -->
	<h2>Pobjednici natjecanja:</h2>
	<ul>
		<c:forEach var="pollOption" items="${winners}">
			<li><a href="${pollOption.link}">${pollOption.name}</a></li>
		</c:forEach>
	</ul>
	
	<hr>
	<a href="index.html">Back to polls.</a>

<%@include file="/WEB-INF/includes/footer.jsp"%>