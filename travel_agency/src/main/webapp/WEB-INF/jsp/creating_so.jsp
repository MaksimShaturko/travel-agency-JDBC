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
	<div>		
	<br>
	<div>${apply_for_tours}:</div>
	<br>

	<form action="Controller" method="post">
		<input type="hidden" name="command" value="create_special_offer" />
		<div>
			<c:if test="${sessionScope.showCountries}">
				<div>${choose_countries}</div>
				<br>
				<c:forEach items="${sessionScope.listOfCountriesNames}"
					var="country">
					<input type="checkbox" name="countryNames" value="${country}">${country}</input>
					<br>
				</c:forEach>
				<br>
				<input type="submit" name="chooseC" value="${open_cities}"></input>
			</c:if>
		</div>
		<c:if test="${showCities && !showCountries && !showHotels}">
			<div>${choose_cities}</div>
			<br>
			<div>
				<c:forEach items="${sessionScope.citiesList}" var="city">
					<input type="checkbox" name="citiesNames" value="${city.name}">${city.name}</input>
					<br>
				</c:forEach>
				<c:if test="${param.message=='noCountries'}">${no_countries}</c:if>
				<br> <input type="submit" name="chooseH" value="${open_hotels}"></input>
			</div>
			<br>
			<button
				onclick="window.location.href = 'Controller?command=create_special_offer&startAgain=start'">${return_to_countries}</button>
			<br>
		</c:if>
		<c:if test="${showHotels}">
			<div>${choose_hotels}</div>
			<br>
			<div>
				<c:forEach items="${sessionScope.hotelsList}" var="hotel">
					<input type="checkbox" name="hotelsNames" value="${hotel.name}">${hotel.name}</input>
					<br>
				</c:forEach>
				<c:if test="${param.message=='noCities'}">
				${no_cities}
			</c:if>
				<br>
				<button
					onclick="window.location.href='Controller?command=create_special_offer&startAgain=start';">${return_to_countries}</button>
				<br>
			</div>
		</c:if>

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
		<br>
		<c:if test="${param.message=='noCountriesToApply'}">
				${no_countries_to_apply}
			</c:if>
		<br>
		<div>
			${choose_from_list}: <select name="specialOffer">
				<c:forEach items="${sessionScope.specialOffers}" var="so">
					<option>${so.title}</option>
				</c:forEach>
			</select> <input type="submit" name="applySO" value="${apply}" />
		</div>${orr}<br><br>${create_for_list}<br><br>${spec_title}
		<br> <input type="text" name="title"
			value="${parameters['title']}" minlength="5" maxlength="50" /> <br>${spec_desc}<br>
		<input type="text" name="description"
			value="${parameters['description']}" minlength="5" maxlength="1000" />
		<br>${spec_disc} <br> <input type="number" name="discount"
			value="${parameters['discount']}" min="1" max="99" /> <br> <br>
		<input type="submit" value="${create_and_apply}" /><br>
		${if_no_chosen}
	</form>
	<br>
</body>
</html>