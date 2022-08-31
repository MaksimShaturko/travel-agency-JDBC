<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="localization.local" var="loc"></fmt:setBundle>

<fmt:message bundle="${loc}" key="locale_label.ru" var="locale_ru" />
<fmt:message bundle="${loc}" key="locale_label.en" var="locale_en" />
<fmt:message bundle="${loc}" key="tour_page.back_to_tours"
	var="back_to_tours" />
<fmt:message bundle="${loc}" key="tour_page.ordered_date"
	var="order_date" />
<fmt:message bundle="${loc}" key="tour_page.actual_ordered_date"
	var="actual_order_date" />
<fmt:message bundle="${loc}" key="tour_page.who_ordered"
	var="who_ordered" />
<fmt:message bundle="${loc}" key="tour_page.hotel" var="just_hotel" />
<fmt:message bundle="${loc}" key="tour_page.hotel_image"
	var="hotel_image" />
<fmt:message bundle="${loc}" key="tour_page.hotel_description"
	var="hotel_description" />
<fmt:message bundle="${loc}" key="tour_page.country" var="just_country" />
<fmt:message bundle="${loc}" key="tour_page.about_country"
	var="about_country" />
<fmt:message bundle="${loc}" key="tour_page.city" var="just_city" />
<fmt:message bundle="${loc}" key="tour_page.city_image" var="city_image" />
<fmt:message bundle="${loc}" key="tour_page.about_city" var="about_city" />
<fmt:message bundle="${loc}" key="tour_page.start_date" var="start_date" />
<fmt:message bundle="${loc}" key="tour_page.return_date"
	var="return_date" />
<fmt:message bundle="${loc}" key="tour_page.stars" var="stars" />
<fmt:message bundle="${loc}" key="tour_page.room" var="room" />
<fmt:message bundle="${loc}" key="tour_page.food" var="food" />
<fmt:message bundle="${loc}" key="tour_page.placement" var="placement" />
<fmt:message bundle="${loc}" key="tour_page.start_price"
	var="start_price" />
<fmt:message bundle="${loc}" key="tour_page.ordered_price"
	var="ordered_price" />
<fmt:message bundle="${loc}" key="tour_page.tour_discount"
	var="tour_discount" />
<fmt:message bundle="${loc}" key="tour_page.user_discount"
	var="user_discount" />
<fmt:message bundle="${loc}" key="tour_page.today_price"
	var="today_price" />
<fmt:message bundle="${loc}" key="tour_page.order" var="order" />
<fmt:message bundle="${loc}" key="tour_page.refuse" var="refuse" />
<fmt:message bundle="${loc}" key="tour_page.message_datetime"
	var="message_datetime" />
<fmt:message bundle="${loc}" key="tour_page.message_from"
	var="message_from" />
<fmt:message bundle="${loc}" key="tour_page.message_text"
	var="message_text" />
<fmt:message bundle="${loc}" key="tour_page.message_manager"
	var="manager" />
<fmt:message bundle="${loc}" key="tour_page.message_manager_you"
	var="manager_you" />
<fmt:message bundle="${loc}" key="tour_page.message_another_manager"
	var="another_manager" />
<fmt:message bundle="${loc}" key="tour_page.spec_create_apply"
	var="spec_create_apply" />
<fmt:message bundle="${loc}" key="tour_page.message_write"
	var="message_write" />
<fmt:message bundle="${loc}" key="tour_page.message_send"
	var="message_send" />
	<fmt:message bundle="${loc}" key="spec.title_invalid"
	var="title_invalid" />
<fmt:message bundle="${loc}" key="spec.description_invalid"
	var="desc_invalid" />
<fmt:message bundle="${loc}" key="spec.parameters_invalid"
	var="params_invalid" />
<fmt:message bundle="${loc}" key="spec.discount_invalid"
	var="discount_invalid" />
<fmt:message bundle="${loc}" key="spec.parameters_and_discount_invalid"
	var="params_and_discount_invalid" />
	<fmt:message bundle="${loc}" key="spec.choose_so_from_list"
	var="choose_from_list" />
<fmt:message bundle="${loc}" key="spec.create_for_list_or_create"
	var="create_for_list" />
<fmt:message bundle="${loc}" key="spec.or" var="orr" />
<fmt:message bundle="${loc}" key="tours_page.create_and_apply"
	var="create_and_apply" />
	<fmt:message bundle="${loc}" key="spec.title" var="spec_title" />
<fmt:message bundle="${loc}" key="spec.description" var="spec_desc" />
<fmt:message bundle="${loc}" key="spec.discount" var="spec_disc" />
<fmt:message bundle="${loc}" key="tours_page.apply" var="apply" />
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<header>
		<jsp:include page="header.jsp" />
	</header>
	<div class="main-div-tours">
		<a href="Controller?command=go_to_tours_page">${back_to_tours}</a> <br>
		<br>
		<table border="1">
			<c:if test="${!requestScope.freeTour}">
				<tr>
					<c:if test="${sessionScope.role == 'CLIENT' }">
						<td>${order_date}</td>
						<td>${sessionScope.order.confirmationDate}</td>
					</c:if>
				</tr>
				<c:if test="${sessionScope.role == 'ADMIN' }">
					<td>${actual_order_date}</td>
					<td>${sessionScope.order.confirmationDate}</td>
					</tr>
					<tr>
						<td>${who_ordered}</td>
						<td>${sessionScope.order.user.details.name}
							${sessionScope.order.user.details.surname}</td>
					</tr>
				</c:if>
			</c:if>
			<tr>
				<td>${just_hotel}</td>
				<td>${sessionScope.orderedTour.hotel.name}</td>
			</tr>
			<tr>
				<td>${hotel_image}</td>
				<td><img style="object-fit: cover; height: 300px; width: 350px"
					src="${request.getContext}/travel_agency/images/hotels/${orderedTour.hotel.image}"
					alt="${tour.hotel.description}" /></td>
			</tr>
			<tr>
				<td>${hotel_description}</td>
				<td>${sessionScope.orderedTour.hotel.description}</td>
			</tr>
			<tr>
				<td>${just_country}</td>
				<td><img style="object-fit: cover; height: 30px; width: 35px"
					src="${request.getContext}/travel_agency/images/flags/${orderedTour.hotel.city.country.image}"
					alt="${sessionScope.orderedTour.hotel.city.country.name} image" />
					${sessionScope.orderedTour.hotel.city.country.name}</td>
			</tr>
			<tr>
				<td>${about_country}</td>
				<td>${sessionScope.orderedTour.hotel.city.country.description}</td>
			</tr>
			<tr>
			<tr>
				<td>${just_city}</td>
				<td>${sessionScope.orderedTour.hotel.city.name}</td>
			</tr>
			<td>${city_image}</td>
			<td><img style="object-fit: cover; height: 300px; width: 350px"
				src="${request.getContext}/travel_agency/images/cities/${orderedTour.hotel.city.image}"
				alt="${sessionScope.orderedTour.hotel.city.name} image" /></td>
			</tr>
			<tr>
				<td>${about_city}</td>
				<td>${sessionScope.orderedTour.hotel.city.description}</td>
			</tr>
			<tr>
				<td>${start_date}</td>
				<td>${sessionScope.orderedTour.dateOfDeparture}</td>
			</tr>
			<tr>
				<td>${return_date}</td>
				<td>${sessionScope.orderedTour.dateOfReturn}</td>
			</tr>

			<tr>
				<td>${stars}</td>
				<td>${sessionScope.orderedTour.hotel.stars}</td>
			</tr>
			<tr>
				<td>${room}</td>
				<td>${sessionScope.orderedTour.room}</td>
			</tr>
			<tr>
				<td>${food}</td>
				<td>${sessionScope.orderedTour.food}</td>
			</tr>
			<tr>
				<td>${placement}</td>
				<td>${sessionScope.orderedTour.placement}</td>
			</tr>
			<tr>
				<td>${start_price}</td>
				<td>${sessionScope.orderedTour.priceStart}</td>
			</tr>
			<c:if test="${!requestScope.freeTour}">
				<tr>
					<td>${ordered_price}</td>
					<td>${sessionScope.orderedTour.realPrice}</td>
				</tr>
			</c:if>
			<c:if test="${requestScope.freeTour}">
				<tr>
					<td>${tour_discount}</td>
					<td>${sessionScope.orderedTour.specialOffer.discount}</td>
				</tr>
				<c:if test="${sessionScope.role == 'CLIENT' }">
					<tr>
						<td>${user_discount}</td>
						<td>${user.details.loyalityDiscount + user.details.agencyAdditionalDiscount}</td>
					</tr>
				</c:if>
				<tr>
					<td>${today_price}</td>
					<td>${sessionScope.orderedTour.realPrice}</td>
				</tr>
			</c:if>
		</table>
		<br>
		<c:if test="${requestScope.freeTour && sessionScope.role == 'CLIENT'}">
			<form action="Controller" method="post">
				<input type="hidden" name="command" value="order_tour" /> <input
					type="hidden" name="tourId" value="${sessionScope.orderedTour.id}" />
				<input type="submit" value="${order}" />
			</form>
		</c:if>

		<c:if
			test="${sessionScope.role == 'CLIENT' && !requestScope.freeTour}">
			<a href="Controller?command=cancel_tour">${refuse}</a>
		</c:if>
		<br> <br>

		<c:if
			test="${!requestScope.freeTour || (requestScope.freeTour && sessionScope.user.role=='ADMIN')}">
			<c:if test="${!sessionScope.messages==null}">
				<table border="1">
					<tr>
						<td>${message_datetime}</td>
						<td>${message_from}</td>
						<td>${message_text}</td>
					</tr>
					<c:forEach items="${sessionScope.messages}" var="message">
						<tr>
							<td>${message.dateTime}</td>
							<c:if test="${sessionScope.user.role == 'CLIENT'}">
								<c:if test="${message.user.role == 'CLIENT'}">
									<c:if test="${sessionScope.user.id == message.user.id}">
										<td>${sessionScope.user.details.name}
											${sessionScope.user.details.surname} (Вы)</td>
									</c:if>
									<c:if test="${sessionScope.user.id != message.user.id}">
										<td>${sessionScope.user.details.name}</td>
									</c:if>
								</c:if>
								<c:if test="${message.user.role == 'ADMIN'}">
									<td>${manager}</td>
								</c:if>
							</c:if>
							<c:if test="${sessionScope.user.role == 'ADMIN'}">
								<c:if test="${message.user.role == 'CLIENT'}">
									<td>${message.user.details.name}
										${message.user.details.surname}</td>
								</c:if>
								<c:if test="${message.user.role == 'ADMIN'}">
									<c:if test="${sessionScope.user.id == message.user.id}">
										<td>${manager_you}</td>
									</c:if>
									<c:if test="${sessionScope.user.id != message.user.id}">
										<td>${sessionScope.user.details.name}
											${sessionScope.user.details.surname} (${another_manger})</td>
									</c:if>
								</c:if>
							</c:if>
							<td>${message.text}</td>
						</tr>
					</c:forEach>
				</table>
			</c:if>


			<c:if
				test="${requestScope.freeTour && sessionScope.user.role == 'ADMIN'}">
				<div class="into-main-div-so">
					<form action="Controller" method="post">
						<input type="hidden" name="command" value="create_special_offer" />
						<input type="hidden" name="tourId" value="${param.tourId}"/>
						<div class="main-text-so">${choose_from_list}:</div>
						<select name="specialOffer">
							<c:forEach items="${sessionScope.specialOffers}" var="so">
								<option>${so.title}</option>
							</c:forEach>
						</select> <input class="so-button-apply" type="submit" name="applySO"
							value="${apply}" />
					</form>
				</div>
				<div class="into-main-div-so-2">
					<div class="title-so">${create_for_list}</div>
					<form action="Controller" method="post">
						<input type="hidden" name="command" value="create_special_offer" />
						<input type="hidden" name="tourId" value="${param.tourId}"/>
						<div class="main-text-so">${spec_title}</div>
						<input type="text" name="title" value="${parameters['title']}"
							minlength="5" maxlength="50" />
						<div class="main-text-so">${spec_desc}</div>
						<input type="text" name="description"
							value="${parameters['description']}" minlength="5"
							maxlength="1000" />
						<div class="main-text-so">${spec_disc}</div>
						<input type="number" name="discount"
							value="${parameters['discount']}" min="1" max="99" />
						<div class="message-so">
							<c:if test="${param.message=='titleInvalid'}">
				${title_invalid}
			</c:if>
							<c:if test="${param.message=='descriptionInvalid'}">
					${desc_invalid}
			</c:if>
							<c:if test="${param.message=='parametersInvalid'}">
					${params_invalid}
			</c:if>
							<c:if test="${param.message=='discountInvalid'}">
					${discount_invalid}
			</c:if>
							<c:if test="${param.message=='parameters_and_discountInvalid'}">
					${params_and_discount_invalid}
			</c:if>
						</div>
						<input class="so-button-2" type="submit"
							value="${create_and_apply}" /><br>
					</form>
				</div>
			</c:if>
			<c:if test="${!requestScope.freeTour}">
				<br> ${message_write}:<br>
				<form action="Controller" method="post">
					<input type="hidden" name="command" value="write_message" /> <input
						type="hidden" name="tourId" value="${sessionScope.orderedTour.id}" />
					<input type="text" name="messageText" size="200" /> <input
						type="submit" value="${message_send}" />
				</form>
			</c:if>
		</c:if>
	</div>
	<footer>
		<jsp:include page="footer.jsp" />
	</footer>
</body>
</html>