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
<title><fmt:message key="registration_page.title" /></title>
</head>
<body>
	<header>
		<jsp:include page="header.jsp" />
	</header>
	<div class="main-div">
		<c:if test="${sessionScope.isDetails == null}">
			<div class="into-main-div-so-2">
				<div class="title-2">
					<fmt:message key="message.register" />
				</div>
				<form action="Controller" method="post">
					<input type="hidden" name="command" value="registration" /><br>
					<div class="main-text-so">
						<fmt:message key="registration_page.email" />
					</div>
					<input type="text" name="email" value="${parameters['email']}" /><br>
					<div class="main-text-so">
						<fmt:message key="registration_page.login" />
					</div>
					<input type="text" name="login" value="${parameters['login']}" /><br>
					<div class="main-text-so">
						<fmt:message key="registration_page.password" />
					</div>
					<input type="password" name="password" placeholder="" />

					<div class="main-text-so">
						<fmt:message key="registration_page.repeat_password" />
					</div>
					<input type="password" name="repeatPassword" placeholder="" />
					<div class="message-so">
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
					</div>
					<input class="so-button-2" type="submit"
						value="<fmt:message key="registration_page.send" />" />
				</form>
			</div>
		</c:if>

		<c:if test="${sessionScope.isDetails != null}">
			<div class="into-main-div-so-2">
				<div class="title-2">
					<fmt:message key="registration_page.add_inf" />
				</div>
				<form action="Controller" method="post">
					<input type="hidden" name="command" value="registration" />

					<div class="main-text-so">
						<fmt:message key="registration_page.name" />
					</div>
					<input type="text" name="name" value="${parameters['name']}" />
					<div class="main-text-so">
						<fmt:message key="registration_page.surname" />
					</div>
					<input type="text" name="surname" value="${parameters['surname']}" />
					<div class="main-text-so">
						<fmt:message key="registration_page.phone_number" />
					</div>
					<input type="text" name="phoneNumber"
						value="${parameters['phoneNumber']}" />
					<div class="message-so">
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
					</div>
					<input class="so-button-2" type="submit"
						value="<fmt:message key="registration_page.save"/>" />
				</form>
			</div>
		</c:if>
	</div>
	<footer>
		<jsp:include page="footer.jsp" />
	</footer>
</body>
</html>