<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/includes/header.jsp"%>

	<!-- Login form -->
	<c:if test="${current_user_id == null}">
		<h3>Log In</h3>
		<form action="/blog/servleti/login" method="post">
			<p>Nick: <input type="text" name="nick" value="${userForm == null ? '' : userForm.nick}">
				<c:if test="${userNotFound != null}">
					<c:out value="${userNotFound}"></c:out>
				</c:if>
			</p>
			
			<p>Password: <input type="password" name="password"></p>
			<input type="submit" value="Log In">
		</form>
		<hr>
	</c:if>
	
	<!-- Registration page link -->
	<h3>Register yourself</h3>
	<a href="/blog/servleti/register">Register</a>
	<br>
	<hr>
	
	<!-- List of authors -->
	<h3>Authors</h3>
	<c:forEach var="user" items="${users}">
		<a href="/blog/servleti/author/${user.nick}">${user.nick}</a> (${user.email})<br>
	</c:forEach>

<%@include file="/WEB-INF/includes/footer.jsp"%>