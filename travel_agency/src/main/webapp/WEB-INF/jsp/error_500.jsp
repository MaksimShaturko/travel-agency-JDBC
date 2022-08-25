<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="localization.local"></fmt:setBundle>
<html>
<head>
<title><fmt:message key="error_500_page.title" /></title>
<meta charset="UTF-8">
</head>
<body>
	<header>
		<jsp:include page="header.jsp" />
	</header>
	<div>
		<h4>
			<fmt:message key="error_500_page.request" />${pageContext.errorData.requestURI}
			<fmt:message key="error_500_page.failed" />
		</h4>
		<br>
		<h4>
			<fmt:message key="error_500_page.code" />${pageContext.errorData.statusCode}
		</h4>
		<br>
		<h4>
			<fmt:message key="error_500_page.error" />
			${sessionScope.errorMessage}
		</h4>
		<br>
	</div>


	<footer>
		<jsp:include page="footer.jsp" />
	</footer>
</body>
</html>