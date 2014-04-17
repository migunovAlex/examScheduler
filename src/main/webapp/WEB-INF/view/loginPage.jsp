<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
 
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Login page</title>
	</head>
	<body>
		<jsp:include page="pageTemplate.jsp" />
		<link rel="stylesheet" type="text/css" href="../../css/loginpage.css" />
		<script type="text/javascript" src="../../js/loginPage.js"></script>
	 
		
		<div id="login-error">
		 	<!--<c:out value="${error}"/>-->
		</div>
		<form id="login" action="../../j_spring_security_check" method="post" >
			<h1>Форма входа</h1>
			 <fieldset id="inputs">			 
				<input id="username" name="j_username" type="text" placeholder="Логин" autofocus required/>
				<input id="password" name="j_password" type="password" placeholder="Пароль" required/>
			</fieldset>
			</br>
			<fieldset>
				<input  type="submit" value="Войти" id="submit"/>       
		 	</fieldset>
		 	</br>
			<span id="errormessage">
				<c:if test="${param.login_error==1}">
					Ошибка при логине
				</c:if>
			</span>
		</form>
		</br>
	</body>
</html>