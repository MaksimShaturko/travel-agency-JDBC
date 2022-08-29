<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="localization.local"></fmt:setBundle>
<html>
<head>
<meta charset="UTF-8">
<title><fmt:message key="registration_page.title" /></title>
</head>
<body>
	<header>
		<jsp:include page="header.jsp" />
	</header>
	<c:if test="${sessionScope.isDetails == null}">
		<form action="Controller" method="post">
			<c:if test="${param.message=='rewriteData'}">
				<fmt:message key="registration_page.rewrite_data" />
			</c:if>
			<c:if test="${param.message=='rewriteLogin'}">
				<fmt:message key="registration_page.rewrite_login" />
			</c:if>
			<c:if test="${param.message=='rewriteEmail'}">
				<fmt:message key="registration_page.rewrite_email" />
			</c:if>
			<c:if test="${param.message=='wrongData'}">
				<fmt:message key="registration_page.wrong_data" />
			</c:if>
			<c:if test="${param.message=='loginInvalid'}">
				<fmt:message key="registration_page.login_invalid" />
			</c:if>
			<c:if test="${param.message=='emailInvalid'}">
				<fmt:message key="registration_page.email_invalid" />
			</c:if>
			<c:if test="${param.message=='dataInvalid'}">
				<fmt:message key="registration_page.data_invalid" />
			</c:if>
			<c:if test="${param.message=='passwordInvalid'}">
				<fmt:message key="registration_page.password_invalid" />
			</c:if>
			<br> <input type="hidden" name="command" value="registration" /><br>
			<fmt:message key="registration_page.email" />
			<br>
			<input type="text" name="email" value="${parameters['email']}" /><br>
			<fmt:message key="registration_page.login" />
			<br>
			<input type="text" name="login" value="${parameters['login']}" /><br>
			<fmt:message key="registration_page.password" />
			<br>
			<input type="password" name="password" placeholder="" /> <br>
			<fmt:message key="registration_page.repeat_password" />
			<br>
			<input type="password" name="repeatPassword" placeholder="" /> <input
				type="submit" value="<fmt:message key="registration_page.send" />" />
		</form>
	</c:if>

	<c:if test="${sessionScope.isDetails != null}">
		<fmt:message key="registration_page.add_inf" />
		<br>
		<br>

		<c:if test="${param.message=='nameInvalid'}">
			<fmt:message key="registration_page.name_invalid" />
		</c:if>
		<c:if test="${param.message=='surnameInvalid'}">
			<fmt:message key="registration_page.surname_invalid" />
		</c:if>
		<c:if test="${param.message=='namesInvalid'}">
			<fmt:message key="registration_page.names_invalid" />
		</c:if>
		<c:if test="${param.message=='phoneNumberInvalid'}">
			<fmt:message key="registration_page.phone_number_invalid" />
		</c:if>
		<c:if test="${param.message=='detailsInvalid'}">
			<fmt:message key="registration_page.details_invalid" />
		</c:if>
		<br>
		<form action="Controller" method="post">
			<input type="hidden" name="command" value="registration" /><br>
			<br>
			<fmt:message key="registration_page.name" />
			<br>
			<input type="text" name="name" value="${parameters['name']}" /><br>
			<br>
			<fmt:message key="registration_page.surname" />
			<br>
			<input type="text" name="surname" value="${parameters['surname']}" /><br>
			<br>
			<fmt:message key="registration_page.phone_number" />
			<br>
			<input type="text" name="phoneNumber"
				value="${parameters['phoneNumber']}" /><br> <br> <input
				type="submit" value="<fmt:message key="registration_page.save"/>" />
		</form>
	</c:if>
	<br>
	<br>
	<a href="Controller?command=go_to_main_page"><fmt:message
			key="registration_page.to_main" /></a>

	<footer>
		<jsp:include page="footer.jsp" />
	</footer>
</body>
</html>