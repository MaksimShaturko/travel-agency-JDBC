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
<fmt:message bundle="${loc}" key="main_user_page.title" var="user_title" />
<fmt:message bundle="${loc}" key="main_guest_page.title"
	var="guest_title" />

<fmt:message bundle="${loc}" key="tours_page.location" var="location" />
<fmt:message bundle="${loc}" key="tour_page.hotel_image"
	var="hotel_image" />
<fmt:message bundle="${loc}" key="tour_page.start_date" var="start_date" />
<fmt:message bundle="${loc}" key="tour_page.return_date"
	var="return_date" />
<fmt:message bundle="${loc}" key="tour_page.stars" var="stars" />
<fmt:message bundle="${loc}" key="tour_page.room" var="room" />
<fmt:message bundle="${loc}" key="tour_page.food" var="food" />
<fmt:message bundle="${loc}" key="tour_page.placement" var="placement" />
<fmt:message bundle="${loc}" key="tours_page.purchase_price"
	var="purchase_price" />
<fmt:message bundle="${loc}" key="tour_page.hotel" var="just_hotel" />
<fmt:message bundle="${loc}" key="tour_page.country" var="just_country" />
<fmt:message bundle="${loc}" key="tour_page.city" var="just_city" />
<fmt:message bundle="${loc}" key="tours_page.open_tour" var="open_tour" />
<fmt:message bundle="${loc}" key="tours_page.start_price"
	var="start_price" />
<fmt:message bundle="${loc}" key="tours_page.so_discount"
	var="so_discount" />
<fmt:message bundle="${loc}" key="tours_page.user_discount"
	var="user_discount" />
<fmt:message bundle="${loc}" key="tours_page.price_including_discounts"
	var="price_with_discounts" />
<fmt:message bundle="${loc}" key="message.special_for_today"
	var="special_for_today" />

<html>
<head>

<meta charset="UTF-8">
<title><c:if test="${!auth}">
${guest_title}</c:if> <c:if test="${auth}">
		<c:if test="${role=='ADMIN'}">${admin_title}</c:if>
		<c:if test="${role=='CLIENT'}">${user_title}</c:if>
	</c:if></title>
</head>
<body>
	<header>
		<jsp:include page="header.jsp" />
	</header>
	<div class="main-div-tours">

		<c:if test="${!auth}">
			<div class="auth-div">
				<div class="auth-info-message">${auth_to_enter}</div>
				<c:if test="${param.message == 'rewriteData'}">
					<div class="auth-wrong-message">${rewriteData}</div>
				</c:if>
				<div class="auth-form">
					<form action="Controller" method="post">
						<input type="hidden" name="command" value="authorization" /> <input
							class="auth-input-text" type="text" name="login"
							placeholder="${login}" /> <input class="auth-input-text"
							type="password" name="password" placeholder="${password}" /> <input
							class="auth-input-submit" type="submit" value="${log_in}" />
					</form>
				</div>
			</div>
		</c:if>


		<c:if test="${auth}">
			<div class="main-div">
				<c:if test="${role=='ADMIN'}">
					<div class="into-main-div">
						<div class="text2">
							<a href="Controller?command=filling_db">${filling_db}</a>
						</div>
						<div class="text2">
							<a href="Controller?command=generate_tours">${gen_tours}</a>
						</div>
					</div>
				</c:if>
				<div class="auth-div">
					<div class="text">
						<h3>${select_tour}:</h3>
					</div>
					<div class="auth-form">
						<form action="Controller" method="post">
							<input type="hidden" name="command" value="get_chosen_tours" />
							<div class="form-text">${choose_country}:</div>
							<select name="country">
								<c:forEach items="${sessionScope.countries}" var="country">
									<option>${country}</option>
								</c:forEach>
							</select>
							<div class="form-text">${choose_rest}:</div>
							<select name="category">
								<c:forEach items="${sessionScope.categories}" var="category">
									<option>${category}</option>
								</c:forEach>
							</select>

							<div class="form-text">${budget}:</div>
							<div>
								<input type="text" name="priceFrom" /> - <input type="text"
									name="priceTo" />
							</div>
							<div class="form-text">${number_of_people}:</div>
							<div>
								<select name="numberOfPeople">
									<c:forEach items="${sessionScope.numbers.keySet()}"
										var="number">
										<option>${number}</option>
									</c:forEach>
								</select>
							</div>
							<div class="form-text">${date_departure}:</div>

							<div class="form-group">
								<input type="date" name="dateDeparture" class="form-control">
							</div>
							<div class="form-text">${days}:</div>
							<div>
								${from} <select name="durationFrom">
									<c:forEach items="${sessionScope.durations}" var="duration">
										<option>${duration}</option>
									</c:forEach>
								</select> - ${to} <select name="durationTo">
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
							<input class="auth-input-submit" type="submit"
								value="${select_tour}" />
						</form>
					</div>
				</div>
			</div>

${special_for_today}
			<div class="main-div-tours">
				<table border="1">
					<tr>
						<td>${location}</td>
						<td>${hotel_image}</td>
						<td>${stars}</td>
						<td>${start_date}</td>
						<td>${return_date}</td>
						<td>${room}</td>
						<td>${food}</td>
						<td>${placement}</td>
						<td>${start_price}</td>
						<td>${so_discount}</td>
						<td>${user_discount}</td>
						<td>${price_with_discounts}</td>
					</tr>
					<c:forEach items="${sessionScope.toursList}" var="tour">
						<c:if test="${sessionScope.role == 'ADMIN' }">
							<form action="Controller" method="post">
								<input type="hidden" name="command" value="view_tour" /> <input
									type="hidden" name="tourId" value="${tour.id}" />
								<tr>
									<td>${just_country}:${tour.hotel.city.country.name}<br>
										${just_city}: ${tour.hotel.city.name}<br> ${just_hotel}:
										${tour.hotel.name}
									</td>
									<td><img
										style="object-fit: cover; height: 200px; width: 250px"
										src="${request.getContext}/travel_agency/images/hotels/${tour.hotel.image}"
										alt="${tour.hotel.description}" /></td>
									<td>${tour.hotel.stars}</td>
									<td>${tour.dateOfDeparture}</td>
									<td>${tour.dateOfReturn}</td>
									<td>${tour.room}</td>
									<td>${tour.food}</td>
									<td>${tour.placement}</td>
									<td>${tour.priceStart}</td>
									<td>${tour.specialOffer.discount}</td>
									<td>${user.details.loyalityDiscount + user.details.agencyAdditionalDiscount}</td>
									<td>${tour.realPrice}</td>
									<c:if test="${sessionScope.role == 'CLIENT' }">
									</c:if>
									<td><input class="button-discount" type="submit"
										value="${open_tour}" /></td>
								</tr>
							</form>
						</c:if>
						<c:if test="${sessionScope.role == 'CLIENT' }">
							<tr>
								<td>${just_country}:${tour.hotel.city.country.name}<br>
									${just_city}: ${tour.hotel.city.name}<br> ${just_hotel}:
									${tour.hotel.name}
								</td>
								<td><img
									style="object-fit: cover; height: 200px; width: 250px"
									src="${request.getContext}/travel_agency/images/hotels/${tour.hotel.image}"
									alt="${tour.hotel.description}" /></td>
								<td>${tour.hotel.stars}</td>
								<td>${tour.dateOfDeparture}</td>
								<td>${tour.dateOfReturn}</td>
								<td>${tour.room}</td>
								<td>${tour.food}</td>
								<td>${tour.placement}</td>
								<td>${tour.priceStart}</td>
								<td>${tour.specialOffer.discount}</td>
								<td>${user.details.loyalityDiscount + user.details.agencyAdditionalDiscount}</td>
								<td>${tour.realPrice}</td>
								<td><form action="Controller" method="post">
										<input type="hidden" name="command" value="order_tour" /> <input
											type="hidden" name="tourId" value="${tour.id}" /> <input
											class="button-discount" type="submit" value="${order_tour}" />
									</form> <br>
									<form action="Controller" method="post">
										<input type="hidden" name="command" value="view_tour" /> <input
											type="hidden" name="tourId" value="${tour.id}" /> <input
											class="button-discount" type="submit" value="${open_tour}" />
									</form></td>
							</tr>
						</c:if>
					</c:forEach>
				</table>
			</div>

		</c:if>











	</div>
	<footer>
		<jsp:include page="footer.jsp" />
	</footer>
</body>
</html>