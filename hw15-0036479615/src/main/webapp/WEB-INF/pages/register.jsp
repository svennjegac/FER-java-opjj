<%@page import="java.util.Map"%>
<%@page import="hr.fer.zemris.java.tecaj_13.model.BlogUserForm"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/includes/header.jsp"%>

	<!-- Register form -->
	<%
		BlogUserForm form = (BlogUserForm) request.getAttribute("userForm");
		Map<String, String> errors = form == null ? null : form.getErrors();
	%>
	<form action="/blog/servleti/register" method="post">
		<p>First Name:	<input type="text" name="firstName" value="${userForm != null ? userForm.firstName : ''}">	</p>
		<p>Last Name:	<input type="text" name="lastName" 	value="${userForm != null ? userForm.lastName : ''}">	</p>
		
		<p>
			E-Mail:<input type="text" name="email" value="${userForm != null ? userForm.email : ''}">
			<%
				if (errors != null && errors.get("email") != null) {
					out.print(errors.get("email"));
				}
			%>
		</p>
		
		<p>
			Nick:<input type="text" name="nick" value="${userForm != null ? userForm.nick : ''}">
			<%
				if (errors != null && errors.get("nick") != null) {
					out.print(errors.get("nick"));
				}
			%>
		</p>
		<p>Password:<input type="password" name="password"></p>
		<input type="submit" value="Register">
	</form>
	
	<!-- Back to main -->
	<a href="/blog/servleti/main">Back to login page</a>
	
	<!-- List of authors -->

<%@include file="/WEB-INF/includes/footer.jsp"%>