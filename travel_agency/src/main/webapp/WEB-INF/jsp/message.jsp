<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="localization.local" />
<html>
<head>
<meta charset="UTF-8"/>
<meta http-equiv="refresh" content="3;URL=Controller?command=go_to_main_page"/>
<title><fmt:message key="message_page.title" /></title>
</head>
<body>
	<header>
		<jsp:include page="header.jsp" />
	</header>
	<br>
	<c:if test="${accessError}">
		<h2>
			<fmt:message key="message_page.access_error" />
		</h2>
	</c:if>
	<c:if test="${registrationSuccess}">
		<h2>
			<fmt:message key="message_page.registration_success" />
		</h2>
	</c:if>
	<c:if test="${applySOSuccess}">
		<h2>
			<fmt:message key="message_page.apply_so_success" />
		</h2>
	</c:if>
	<c:if test="${createApplySOSuccess}">
		<h2>
			<fmt:message key="message_page.create_apply_so_success" />
		</h2>
	</c:if>
	<c:if test="${createSOSuccess}">
		<h2>
			<fmt:message key="message_page.create_so_success" />
		</h2>
	</c:if>
	<c:if test="${orderSuccess}">
		<h2>
			<fmt:message key="message_page.order_success" />
		</h2>
	</c:if>
	<br>
	<br>
	<a href="Controller?command=go_to_main_page"><fmt:message
			key="message.return_to_main_page" /></a>
	<footer>
		<jsp:include page="footer.jsp" />
	</footer>
</body>
</html>