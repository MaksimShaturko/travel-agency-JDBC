<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="localization.local" var="loc"></fmt:setBundle>

<fmt:message bundle="${loc}" key="locale_label.ru" var="locale_ru" />
<fmt:message bundle="${loc}" key="locale_label.en" var="locale_en" />
<fmt:message bundle="${loc}" key="message.return_to_main_page"
	var="to_main_page" />
<fmt:message bundle="${loc}" key="message.log_out" var="log_out" />

<fmt:message bundle="${loc}" key="users_page.users_list"
	var="users_list" />
<fmt:message bundle="${loc}" key="users_page.login" var="login" />
<fmt:message bundle="${loc}" key="users_page.email" var="email" />
<fmt:message bundle="${loc}" key="users_page.name" var="name" />
<fmt:message bundle="${loc}" key="users_page.surname" var="surname" />
<fmt:message bundle="${loc}" key="users_page.phone" var="phone" />
<fmt:message bundle="${loc}" key="users_page.number_visited_tours"
	var="number_visited_tours" />
<fmt:message bundle="${loc}" key="users_page.loyality_discount"
	var="loyal_discount" />
<fmt:message bundle="${loc}" key="users_page.additional_discount"
	var="addit_discount" />
<fmt:message bundle="${loc}" key="users_page.enter_additional_discount"
	var="enter_addit_discount" />
<fmt:message bundle="${loc}" key="users_page.set_discount"
	var="set_discount" />
<html>
<head>
<meta charset="UTF-8">
<title>${users_list}</title>
</head>
<body>
	<header>
		<jsp:include page="header.jsp" />
	</header>
	<div class="main-div-users">
		<table border="1">
			<tr>
				<td>${login}</td>
				<td>${email}</td>
				<td>${name}</td>
				<td>${surname}</td>
				<td>${phone}</td>
				<td>${number_visited_tours}</td>
				<td>${loyal_discount}</td>
				<td>${addit_discount}</td>
			</tr>
			<c:forEach items="${sessionScope.listOfUsers}" var="user">
				<tr>
					<td>${user.login}</td>
					<td>${user.email}</td>
					<td>${user.details.name}</td>
					<td>${user.details.surname}</td>
					<td>${user.details.phoneNumber}</td>
					<td>${user.details.numberOfVisitedTours}</td>
					<td>${user.details.loyalityDiscount}</td>
					<form action="Controller" method="post">
						<input type="hidden" name="command" value="change_agency_discount" />
						<input type="hidden" name="details_id" value="${user.details.id}" />
						<td>${user.details.agencyAdditionalDiscount}</td>
						<td><input type="text" name="extra"
							placeholder="${enter_addit_discount}" /> <input class="button-discount" type="submit"
							value="${set_discount}" /></td>
					</form>
				</tr>
			</c:forEach>
		</table>
	</div>
	<footer>
		<jsp:include page="footer.jsp" />
	</footer>
</body>
</html>