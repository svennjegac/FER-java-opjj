<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/includes/header.jsp"%>

	<!-- Blog entries of author -->
	<h3>Blog entries of: "${author.nick}"</h3>	
	<c:forEach var="entry" items="${author.entries}">
		<a href="/blog/servleti/author/${entry.creator.nick}/${entry.id}">${entry.title}</a>
	</c:forEach>
	<hr>
	
	<!-- Add new entry -->
	<c:if test="${current_user_id == author.id}">
		<h3>Add new entry</h3>
		<a href="/blog/servleti/author/${author.nick}/new">Add new entry</a>
		<hr>
	</c:if>
	
	<!-- Back to main page -->
	<a href="/blog/servleti/main">Go to main page</a>

<%@include file="/WEB-INF/includes/footer.jsp"%>