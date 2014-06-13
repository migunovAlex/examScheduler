<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div id="information-block" class="information-block">
	<table class="welcome-message-label">
		<tr>
			<td>
				<div>
					<label id="welcome-message">Добро пожаловать, ${userName}!</label>
				</div>
			</td>
			<td>
				<div class="logout-button">
					<a href="#">Выйти</a>
				</div>
			</td>
		</tr>
	</table>
</div>