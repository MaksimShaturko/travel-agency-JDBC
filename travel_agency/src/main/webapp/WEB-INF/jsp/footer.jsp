<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="localization.local"></fmt:setBundle>

<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
	<div class="footer-div">
		<p class="footer-text">
			<fmt:message key="message.copyright_project" />
			<br>
			<fmt:message key="message.copyright_author" />
		</p>
	</div>
</body>
</html>

