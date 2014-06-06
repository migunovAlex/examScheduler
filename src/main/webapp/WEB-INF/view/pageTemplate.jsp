<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="pathForSecuredResources" value=""/>
<c:if test="${userSession!=null}">
	<c:set var="pathForSecuredResources" value="../"/>
</c:if>
<link rel="stylesheet" type="text/css" href="${pathForSecuredResources}../../css/maintemplate.css" />
<link rel="stylesheet" type="text/css" href="${pathForSecuredResources}../../css/jquery.growl.css" />
<script type="text/javascript" src="${pathForSecuredResources}../../js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="${pathForSecuredResources}../../js/jquery.growl.js"></script>
<script type="text/javascript" src="${pathForSecuredResources}../../js/maintemplate.js"></script>
