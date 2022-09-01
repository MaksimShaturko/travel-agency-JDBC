<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="localization.local" var="loc"></fmt:setBundle>

<fmt:message bundle="${loc}" key="locale_label.ru" var="locale_ru" />
<fmt:message bundle="${loc}" key="locale_label.en" var="locale_en" />
<fmt:message bundle="${loc}" key="spec.title" var="spec_title" />
<fmt:message bundle="${loc}" key="spec.description" var="spec_desc" />
<fmt:message bundle="${loc}" key="spec.discount" var="spec_disc" />
<fmt:message bundle="${loc}" key="spec.create_offer_button"
	var="create_button" />
<fmt:message bundle="${loc}" key="tours_page.message_no_tours"
	var="message_no_tours" />
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
<fmt:message bundle="${loc}" key="tours.spec_no_countries"
	var="no_countries" />
<fmt:message bundle="${loc}" key="message.return_to_main_page"
	var="to_main_page" />
<fmt:message bundle="${loc}" key="message.log_out" var="log_out" />
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
<fmt:message bundle="${loc}" key="tour_page.start_price"
	var="start_price" />
<fmt:message bundle="${loc}" key="tours_page.purchase_price"
	var="purchase_price" />
<fmt:message bundle="${loc}" key="tour_page.hotel" var="just_hotel" />
<fmt:message bundle="${loc}" key="tour_page.country" var="just_country" />
<fmt:message bundle="${loc}" key="tour_page.city" var="just_city" />
<fmt:message bundle="${loc}" key="tours_page.open_tour" var="open_tour" />
<fmt:message bundle="${loc}" key="tours_page.mark_as_visited"
	var="mark_as_visited" />
<fmt:message bundle="${loc}" key="spec.choose_so_from_list"
	var="choose_from_list" />
<fmt:message bundle="${loc}" key="spec.create_for_list_or_create"
	var="create_for_list" />
<fmt:message bundle="${loc}" key="spec.or" var="orr" />
<fmt:message bundle="${loc}" key="tours_page.create_and_apply"
	var="create_and_apply" />
<fmt:message bundle="${loc}" key="tours_page.apply" var="apply" />
<fmt:message bundle="${loc}" key="tours_page.start_price"
	var="start_price" />
<fmt:message bundle="${loc}" key="tours_page.so_discount"
	var="so_discount" />
<fmt:message bundle="${loc}" key="tours_page.user_discount"
	var="user_discount" />
<fmt:message bundle="${loc}" key="tours_page.price_including_discounts"
	var="price_with_discounts" />
<fmt:message bundle="${loc}" key="tours_page.order_tour"
	var="order_tour" />
<fmt:message bundle="${loc}" key="tours_page.title_chosen"
	var="title_chosen" />
<fmt:message bundle="${loc}" key="tours_page.title_ordered"
	var="title_ordered" />
<fmt:message bundle="${loc}" key="tours_page.title_visited"
	var="title_visited" />
<fmt:message bundle="${loc}" key="tours_page.sort_by_price"
	var="sort_by_price" />
<html>
<head>
<meta charset="UTF-8">
<title><c:if test="${sessionScope.toursRequest == 'ordered'}">
${title_ordered}
</c:if> <c:if test="${sessionScope.toursRequest == 'chosen'}">
${title_chosen}
</c:if> <c:if test="${sessionScope.toursRequest == 'visited'}">
${title_visited}
</c:if></title>
</head>
<body>
	<header>
		<jsp:include page="header.jsp" />
	</header>
	<div class="main-div-tours">

		<c:if test="${sessionScope.toursRequest == 'ordered'}">
			<table border="1">
				<tr>
					<td>${location}</td>
					<td>${hotel_image}</td>
					<td>${start_date}</td>
					<td>${return_date}</td>
					<td>${stars}</td>
					<td>${room}</td>
					<td>${food}</td>
					<td>${placement}</td>
					<td>${purchase_price}<br>
					<button class="button-scroll-page"
							onclick="window.location.href = 'Controller?command=go_to_tours_page&sort=up'">
							&#129045;</button>
						<button class="button-scroll-page"
							onclick="window.location.href = 'Controller?command=go_to_tours_page&sort=down'">
							&#129047;</button></td>					
				</tr>
				<c:forEach items="${requestScope.toursOnPage}" var="tour">
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
							<td>${tour.dateOfDeparture}</td>
							<td>${tour.dateOfReturn}</td>
							<td>${tour.hotel.stars}</td>
							<td>${tour.room}</td>
							<td>${tour.food}</td>
							<td>${tour.placement}</td>
							<td>${tour.realPrice}</td>
							<td><input class="button-discount" type="submit"
								value="${open_tour}" /></td>
					</form>
					<c:if test="${sessionScope.role == 'ADMIN' }">
						<td><form action="Controller" method="post">
								<input type="hidden" name="command" value="set_tour_as_visited" />
								<input type="hidden" name="tourId" value="${tour.id}" />
								<td><input class="button-discount" type="submit"
									value="${mark_as_visited}" /></td>
							</form></td>
					</c:if>
					</tr>
				</c:forEach>
			</table>
		</c:if>

		<c:if test="${sessionScope.toursRequest == 'chosen'}">

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
					<td>${price_with_discounts}<br>
					<button class="button-scroll-page"
							onclick="window.location.href = 'Controller?command=go_to_tours_page&sort=up'">
							&#129045;</button>
						<button class="button-scroll-page"
							onclick="window.location.href = 'Controller?command=go_to_tours_page&sort=down'">
							&#129047;</button></td>
				</tr>
				<c:forEach items="${requestScope.toursOnPage}" var="tour">
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




		</c:if>

		<c:if test="${sessionScope.toursRequest == 'visited'}">
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
				</tr>
				<c:forEach items="${requestScope.toursOnPage}" var="tour">
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
					</tr>
				</c:forEach>
			</table>
		</c:if>

		<div class="pagination">
			<c:if test="${requestScope.previous != null}">
				<button class="button-scroll-page"
					onclick="window.location.href = 'Controller?command=go_to_tours_page&pageNumber=${requestScope.pageNumber - 1}'"><</button>
			</c:if>
			${requestScope.pageNumber}
			<c:if test="${requestScope.next != null}">
				<button class="button-scroll-page"
					onclick="window.location.href = 'Controller?command=go_to_tours_page&pageNumber=${requestScope.pageNumber + 1}'">></button>
			</c:if>
		</div>


		<c:if test="${sessionScope.toursRequest == 'chosen'}">
			<c:if test="${sessionScope.role == 'ADMIN'}">
				<br>
				<div class="into-main-div-so">
					<c:if test="${param.message=='messageNoCountries'}">
					${no_countries}
			</c:if>
					<form action="Controller" method="post">
						<input type="hidden" name="command" value="create_special_offer" />
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
		</c:if>



		<c:if test="${sessionScope.toursRequest == 'isEmpty'}">
	${message_no_tours}
	</c:if>
	</div>

	<footer>
		<jsp:include page="footer.jsp" />
	</footer>
</body>
</html>