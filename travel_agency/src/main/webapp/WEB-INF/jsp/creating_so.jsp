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
<fmt:message bundle="${loc}" key="spec.no_countries" var="no_countries" />
<fmt:message bundle="${loc}" key="spec.no_cities" var="no_cities" />
<fmt:message bundle="${loc}" key="spec.no_countries_to_apply"
	var="no_countries_to_apply" />
<fmt:message bundle="${loc}" key="spec.title" var="title" />
<fmt:message bundle="${loc}" key="spec.apply_for_tours"
	var="apply_for_tours" />
<fmt:message bundle="${loc}" key="spec.choose_countries"
	var="choose_countries" />
<fmt:message bundle="${loc}" key="spec.choose_cities"
	var="choose_cities" />
<fmt:message bundle="${loc}" key="spec.choose_hotels"
	var="choose_hotels" />
<fmt:message bundle="${loc}" key="spec.return_to_countries"
	var="return_to_countries" />
<fmt:message bundle="${loc}" key="message.return_to_main_page"
	var="to_main_page" />
<fmt:message bundle="${loc}" key="spec.choose_so_from_list"
	var="choose_from_list" />
<fmt:message bundle="${loc}" key="spec.create_for_list_or_create"
	var="create_for_list" />
<fmt:message bundle="${loc}" key="spec.or" var="orr" />
<fmt:message bundle="${loc}" key="spec.if_no_chosen" var="if_no_chosen" />
<fmt:message bundle="${loc}" key="message.log_out" var="log_out" />
<fmt:message bundle="${loc}" key="spec.open_cities" var="open_cities" />
<fmt:message bundle="${loc}" key="spec.open_hotels" var="open_hotels" />
<fmt:message bundle="${loc}" key="spec.apply" var="apply" />
<fmt:message bundle="${loc}" key="spec.create_and_apply"
	var="create_and_apply" />
<html>
<head>
<meta charset="UTF-8">
<title>${title}</title>
</head>
<body>
	<header>
		<jsp:include page="header.jsp" />
	</header>
	<form action="Controller" method="post">
		<div class="main-div">
			<input type="hidden" name="command" value="create_special_offer" />
			<div class="into-main-div-so">
				<c:if test="${sessionScope.showCountries}">
					<div class="title-so">${choose_countries}</div>
					<div class="main-text-so">
						<c:forEach items="${sessionScope.listOfCountriesNames}"
							var="country">
							<input type="checkbox" name="countryNames" value="${country}">${country}</input>
							<br>
						</c:forEach>
					</div>
					<input class="so-button" type="submit" name="chooseC"
						value="${open_cities}"></input>
				</c:if>
				<c:if test="${showCities && !showCountries && !showHotels}">
					<div class="title-so">${choose_cities}</div>
					<c:forEach items="${sessionScope.citiesList}" var="city">
						<input type="checkbox" name="citiesNames" value="${city.name}">${city.name}</input>
						<br>
					</c:forEach>
					<c:if test="${param.message=='noCountries'}">${no_countries}</c:if>
					<input class="so-button" type="submit" name="chooseH"
						value="${open_hotels}"></input>
					<button class="so-button-country"
						onclick="window.location.href = 'Controller?command=create_special_offer&startAgain=start'">${return_to_countries}</button>
					<br>
				</c:if>
				<c:if test="${showHotels}">
					<div>${choose_hotels}</div>
					<c:forEach items="${sessionScope.hotelsList}" var="hotel">
						<input type="checkbox" name="hotelsNames" value="${hotel.name}">${hotel.name}</input>
						<br>
					</c:forEach>
					<c:if test="${param.message=='noCities'}">
				${no_cities}
			</c:if>
					<button class="so-button-country"
						onclick="window.location.href='Controller?command=create_special_offer&startAgain=start';">${return_to_countries}</button>
				</c:if>

				<br>
				<c:if test="${param.message=='noCountriesToApply'}">
				${no_countries_to_apply}
			</c:if>
				<div class="main-text-so">${choose_from_list}:</div>
				<select name="specialOffer">
					<c:forEach items="${sessionScope.specialOffers}" var="so">
						<option>${so.title}</option>
					</c:forEach>
				</select> <input class="so-button-apply" type="submit" name="applySO"
					value="${apply}" />
			</div>
			<div class="into-main-div-so-2">
				<div class="title-so">${create_for_list}</div>
				<div class="main-text-so">${spec_title}</div>
				<input type="text" name="title" value="${parameters['title']}"
					minlength="5" maxlength="50" />
				<div class="main-text-so">${spec_desc}</div>
				<input type="text" name="description" style="width: 300px;"
					value="${parameters['description']}" minlength="5" maxlength="1000" />
				<br>
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
				<input class="so-button-2" type="submit" value="${create_and_apply}" />
				<div class="small-text-so">${if_no_chosen}</div>
			</div>
		</div>
	</form>
	<footer>
		<jsp:include page="footer.jsp" />
	</footer>
</body>
</html>