<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/includes/header.jsp"%>

	<c:forEach var="poll" items="${polls}">
		<a href="glasanje?pollID=${poll.id}">${poll.title}</a><br>
	</c:forEach>

<%@include file="/WEB-INF/includes/footer.jsp"%>