<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>Главная</title>
</head>
<body>
	<jsp:include page="pageTemplate.jsp"/>
	
	<c:if test="${userSession!=null}">
		<jsp:include page="informationBlock.jsp"/>
		<jsp:include page="mainPageMenu.jsp"/>
	</c:if>
	<h1>Main Page</h1>
	<c:out value="${userSession}"/>
	<c:if test="${userSession!=null}">
		<jsp:include page="footerBlock.jsp"/>
	</c:if>
</body>
</html>