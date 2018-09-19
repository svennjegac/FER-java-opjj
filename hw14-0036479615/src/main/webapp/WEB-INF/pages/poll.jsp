<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/includes/header.jsp"%>

	<h1>${poll.title}</h1>
	<h4>${poll.message}</h4>
	
	<hr>
	<ol>
		<c:forEach var="pollOption" items="${pollOptions}">
			<li><a href="glasanje-glasaj?pollOptionID=${pollOption.id}">${pollOption.name}</a></li>
		</c:forEach>
	</ol>
	
	<hr>
	<a href="index.html">Back to polls.</a>

<%@include file="/WEB-INF/includes/footer.jsp"%>