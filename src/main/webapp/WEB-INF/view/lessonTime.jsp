<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="../../../css/jquery-ui-1.9.0.custom.css" />
<link rel="stylesheet" type="text/css" href="../../../css/lessonsTime.css" />
<title>Lesson Time</title>
</head>
<body>
	<jsp:include page="pageTemplate.jsp" />
	<script type="text/javascript" src="../../../js/lessonsTime.js"></script>
	<c:if test="${userSession!=null}">
		<jsp:include page="informationBlock.jsp" />
		<jsp:include page="mainPageMenu.jsp" />
		<div id="lessons-time-table">
			<div id="jqLessonsTimeGrid" class="centered-div">
				<table id="lessonsTime"></table>
				<div id="lessonsTimePager"></div>
			</div>
		</div>
		<jsp:include page="footerBlock.jsp" />
	</c:if>
</body>
</html>