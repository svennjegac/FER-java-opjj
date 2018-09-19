<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/includes/header.jsp"%>

	<a href="setcolor?${SetColorServlet.COLOR_PARAM}=${SetColorServlet.WHITE}">WHITE</a><br>
	<a href="setcolor?${SetColorServlet.COLOR_PARAM}=${SetColorServlet.RED}">RED</a><br>
	<a href="setcolor?${SetColorServlet.COLOR_PARAM}=${SetColorServlet.GREEN}">GREEN</a><br>
	<a href="setcolor?${SetColorServlet.COLOR_PARAM}=${SetColorServlet.CYAN}">CYAN</a><br>

	<br>
	<a href="index.jsp">Back to index</a>
	
<%@ include file="/WEB-INF/includes/footer.jsp"%>