<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/includes/header.jsp"%>

	<!-- Edit/Update form -->
	<form action="/blog/servleti/updateentry" method="post">
		<p>
			Title:<input type="text" name="title" value="${entryForm.title}">
			${errors.title != null ? errors.title : ''}
		</p>
		<p>
			Text:<input type="text" name="text" value="${entryForm.text}">
			${errors.text != null ? errors.text : ''}
		</p>
		<input type="text" value="${author.nick}" hidden="true" name="nick">
		<input type="text" value="${entryForm.id}" hidden="true" name="entryId">
		<input type="submit" value="Add/Update">
	</form>
	
	<!-- Back to main page -->
	<a href="/blog/servleti/main">Go to main page</a>

<%@include file="/WEB-INF/includes/footer.jsp"%>