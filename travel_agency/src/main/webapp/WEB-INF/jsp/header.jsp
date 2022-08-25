<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="localization.local" var="loc"></fmt:setBundle>

<fmt:message bundle="${loc}" key="message.register"
	var="register_message" />
<fmt:message bundle="${loc}" key="locale_label.ru" var="locale_ru" />
<fmt:message bundle="${loc}" key="locale_label.en" var="locale_en" />
<fmt:message bundle="${loc}" key="message.return_to_main_page"
	var="to_main_page" />
<fmt:message bundle="${loc}" key="message.register"
	var="register_message" />
<fmt:message bundle="${loc}" key="locale_label.ru" var="locale_ru" />
<fmt:message bundle="${loc}" key="locale_label.en" var="locale_en" />
<fmt:message bundle="${loc}" key="message.create_special_offer"
	var="spec_offer" />
<fmt:message bundle="${loc}" key="main_admin_page.get_all_users"
	var="get_all_users" />
<fmt:message bundle="${loc}" key="main_admin_page.all_ordered_tours"
	var="all_ordered_tours" />
<fmt:message bundle="${loc}" key="message.log_out" var="log_out" />
<fmt:message bundle="${loc}" key="main_user_page.user_ordered_tours" var="user_ordered_tours" />
<fmt:message bundle="${loc}" key="main_user_page.user_visited_tours" var="user_visited_tours" />

<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>
	<main class="main">
		<div class="navbar">
			<div class="nav-button">
				<form action="Controller" method="post">
					<input type="hidden" name="command" value="go_to_main_page" /> <input
						class="nav-submit" type="submit" value="${to_main_page}" />
				</form>
			</div>
			<c:if test="${!auth}">
				<div class="nav-button">
					<form action="Controller" method="post">
						<input type="hidden" name="command"
							value="go_to_registration_page" /> <input class="nav-submit"
							type="submit" value="${register_message}" />
					</form>
				</div>
			</c:if>
			<c:if test="${role.equals('ADMIN')}">
				<div class="nav-button">
					<form action="Controller" method="post">
						<input type="hidden" name="command"
							value="go_to_creating_special_offer_page" /> <input
							class="nav-submit" type="submit" value="${spec_offer}" />
					</form>
				</div>
				<div class="nav-button">
					<form action="Controller" method="post">
						<input type="hidden" name="command" value="get_all_users" /> <input
							class="nav-submit" type="submit" value="${get_all_users}" />
					</form>
				</div>
				<div class="nav-button">
					<form action="Controller" method="post">
						<input type="hidden" name="command" value="get_ordered_tours" />
						<input class="nav-submit" type="submit"
							value="${all_ordered_tours}" />
					</form>
				</div>
			</c:if>
			<c:if test="${role.equals(\"CLIENT\")}">
				<div class="nav-button">
					<form action="Controller" method="post">
						<input type="hidden" name="command" value="get_ordered_tours" />
						<input class="nav-submit" type="submit"
							value="${user_ordered_tours}" />
					</form>
				</div>
				<div class="nav-button">
					<form action="Controller" method="post">
						<input type="hidden" name="command" value="get_visited_Tours" />
						<input class="nav-submit" type="submit"
							value="${user_visited_tours}" />
					</form>
				</div>
			</c:if>
			<div class="nav-button-space"></div>
			<c:if test="${auth}">
				<div class="nav-button-last">
					<form action="Controller" method="post">
						<input type="hidden" name="command" value="log_out" /> <input
							class="nav-submit" type="submit" value="${log_out}" />
					</form>
				</div>
			</c:if>
			<div class="nav-button-last">
				<form action="Controller" method="post">
					<input type="hidden" name="command" value="change_locale" /> <input
						type="hidden" name="language" value="ru" /> <input
						class="nav-submit" type="submit" value="${locale_ru}" />
				</form>
			</div>
			|
			<div class="nav-button-last">
				<form action="Controller" method="post">
					<input type="hidden" name="command" value="change_locale" /> <input
						type="hidden" name="language" value="en" /> <input
						class="nav-submit" type="submit" value="${locale_en}" />
				</form>
			</div>



		</div>
	</main>
</body>
</html>

