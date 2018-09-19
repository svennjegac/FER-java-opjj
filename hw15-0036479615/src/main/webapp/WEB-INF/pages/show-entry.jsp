<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/includes/header.jsp"%>

	<!-- Entry -->
	<h3>${entry.title}</h3>
	<p>${entry.text}</p>
	<p>
		Creator: ${entry.creator.nick}<br>
		Created: ${entry.createdAt}<br>
		Modified: ${entry.lastModifiedAt}
	</p>
	
	<!-- Edit link -->
	<c:if test="${entry.creator.id == current_user_id}">
		<a href="/blog/servleti/author/${entry.creator.nick}/edit?id=${entry.id}">Edit post</a>
	</c:if>
	
	<c:forEach var="comment" items="${entry.comments}">
		<hr>
		<p>
			${comment.message}, (${comment.email}, ${comment.postedOn})<br>
		</p>
	</c:forEach>
	
	<form action="/blog/servleti/comment" method="post">
		Comment: <input type="text" name="comment">
				<input type="text" name="id" value="${entry.id}" hidden="true">
		<input type="submit" value="Comment">
	</form>
	
	<!-- Back to main -->
	<a href="/blog/servleti/main">Back to login page</a>

<%@include file="/WEB-INF/includes/footer.jsp"%>