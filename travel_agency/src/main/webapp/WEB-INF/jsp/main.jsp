<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<!DOCTYPE html>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="localization.local" var="loc"></fmt:setBundle>

<fmt:message bundle="${loc}" key="message.register"
	var="register_message" />
<fmt:message bundle="${loc}" key="authorization_page.auth_to_enter"
	var="auth_to_enter" />
<fmt:message bundle="${loc}" key="locale_label.ru" var="locale_ru" />
<fmt:message bundle="${loc}" key="locale_label.en" var="locale_en" />
<fmt:message bundle="${loc}" key="message.enter_login" var="login" />
<fmt:message bundle="${loc}" key="message.enter_password" var="password" />
<fmt:message bundle="${loc}" key="message.log_in" var="log_in" />
<fmt:message bundle="${loc}" key="message.filling_db" var="filling_db" />
<fmt:message bundle="${loc}" key="message.generate_tours"
	var="gen_tours" />
<fmt:message bundle="${loc}" key="message.create_special_offer"
	var="spec_offer" />
<fmt:message bundle="${loc}" key="tour_selection.select"
	var="select_tour" />
<fmt:message bundle="${loc}" key="tour_selection.choose_type"
	var="choose_type" />
<fmt:message bundle="${loc}" key="tour_selection.date_departure"
	var="date_departure" />
<fmt:message bundle="${loc}" key="tour_selection.days" var="days" />
<fmt:message bundle="${loc}" key="tour_selection.from" var="from" />
<fmt:message bundle="${loc}" key="tour_selection.to" var="to" />
<fmt:message bundle="${loc}" key="tour_selection.type_food"
	var="type_food" />
<fmt:message bundle="${loc}" key="tour_selection.placement"
	var="placement" />
<fmt:message bundle="${loc}" key="tour_selection.room" var="room" />
<fmt:message bundle="${loc}" key="tour_selection.budget" var="budget" />
<fmt:message bundle="${loc}" key="tour_selection.choose_country"
	var="choose_country" />
<fmt:message bundle="${loc}" key="authorization_page.rewrite_data"
	var="rewriteData" />
<fmt:message bundle="${loc}" key="main_page.title" var="title" />
<fmt:message bundle="${loc}" key="message.log_out" var="log_out" />
<fmt:message bundle="${loc}" key="authorization_page.auth_to_enter"
	var="auth_to_enter" />
<fmt:message bundle="${loc}" key="main_admin_page.title"
	var="admin_title" />
<fmt:message bundle="${loc}" key="main_admin_page.get_all_users"
	var="get_all_users" />
<fmt:message bundle="${loc}" key="main_admin_page.all_ordered_tours"
	var="all_ordered_tours" />
<fmt:message bundle="${loc}" key="main_page.choose_rest"
	var="choose_rest" />
<fmt:message bundle="${loc}" key="main_page.number_of_people"
	var="number_of_people" />

<fmt:message bundle="${loc}" key="main_user_page.user_ordered_tours"
	var="user_ordered_tours" />
<fmt:message bundle="${loc}" key="main_user_page.user_visited_tours"
	var="user_visited_tours" />

<html>
<head>

<meta charset="UTF-8">
<title>${title}</title>
</head>
<body>
	<header>
		<jsp:include page="header.jsp" />
	</header>
	<main>
		<c:if test="${!auth}">
			<div class="auth-div">
				<div class="auth-info-message">${auth_to_enter}</div>
				<c:if test="${param.message == 'rewriteData'}">
					<div class="auth-wrong-message">${rewriteData}</div>
				</c:if>
				<div class="auth-form">
					<form action="Controller" method="post">
						<input type="hidden" name="command" value="authorization" /> <input class="auth-input-text"
							type="text" name="login" placeholder="${login}" /> <input class="auth-input-text"
							type="password" name="password" placeholder="${password}" /> <input
							type="submit" value="${log_in}" />
					</form>
				</div>
			</div>
		</c:if>

		<c:if test="${auth}">
			<c:if test="${role=='ADMIN'}">
				<h4>${admin_title}</h4>
				<br>
				<br>
				<a href="Controller?command=filling_db">${filling_db}</a>
				<br>
				<br>
				<a href="Controller?command=generate_tours">${gen_tours}</a>
			</c:if>
			<div>
				<h3>${select_tour}:</h3>
			</div>
			<form action="Controller" method="post">
				<input type="hidden" name="command" value="get_chosen_tours" />
				<div>
					${choose_country}: <select name="country">
						<c:forEach items="${sessionScope.countries}" var="country">
							<option>${country}</option>
						</c:forEach>
					</select>
				</div>

				<div>
					${choose_rest}: <select name="category">
						<c:forEach items="${sessionScope.categories}" var="category">
							<option>${category}</option>
						</c:forEach>
					</select>
				</div>
				${budget}:
				<div>
					<input type="text" name="priceFrom" /> - <input type="text"
						name="priceTo" />
				</div>
				${number_of_people}:
				<div>
					<select name="numberOfPeople">
						<c:forEach items="${sessionScope.numbers.keySet()}" var="number">
							<option>${number}</option>
						</c:forEach>
					</select>
				</div>
				${date_departure}:

				<div class="form-group">
					<input type="date" name="dateDeparture" class="form-control">
				</div>

				${days}: <br>
				<div>
					${from} <select name="durationFrom">
						<c:forEach items="${sessionScope.durations}" var="duration">
							<option>${duration}</option>
						</c:forEach>
					</select>
				</div>

				<div>
					${to} <select name="durationTo">
						<c:forEach items="${sessionScope.durations}" var="duration">
							<option>${duration}</option>
						</c:forEach>
					</select>
				</div>

				<%-- 			<div>
				${type_food}:
				<c:forEach items="${sessionScope.food}" var="foodType">
					<input type="checkbox" name="food" value="${foodType}">${foodType}</input>
				</c:forEach>
			</div> 
			<div>

				${placement}: 					
					<c:forEach items="${sessionScope.placements}" var="placementType">
					<input type="checkbox" name="typeOfPlacement" value="${placementType}">${placementType}</input>
				</c:forEach>
				
			</div>

		<div>
				${room}:					
					<c:forEach items="${sessionScope.rooms}" var="roomType">
					<input type="checkbox" name="typeOfRoom" value="${roomType}">${roomType}</input>
				</c:forEach>
			</div> 
			--%>

				<div>
					<input type="submit" value="${select_tour}" />
				</div>
			</form>
		</c:if>
		<br>
	</main>
</body>
</html>