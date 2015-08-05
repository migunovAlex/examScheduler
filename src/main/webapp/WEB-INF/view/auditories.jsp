<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="../../../css/style.css" />
<title>Auditory Page</title>
</head>
<body>
	<h1>View and edit of data of auditories</h1>
	<table id="table" class="table">
		 <c:forEach items="${model.listAuditories}" var="element">
                    <tr>
                        <td><c:out value="${element.id}" /></td>
                        <td><c:out value="${element.number}" /></td>
                        <td><c:out value="${element.maxPerson}" /></td>
                    </tr>
         </c:forEach>               
		
	</table>
</body>
</html>