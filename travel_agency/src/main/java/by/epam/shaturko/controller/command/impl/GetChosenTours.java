package by.epam.shaturko.controller.command.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.shaturko.controller.Constant;
import by.epam.shaturko.controller.RequestParameter;
import by.epam.shaturko.controller.SessionAttribute;
import by.epam.shaturko.controller.command.Command;
import by.epam.shaturko.entity.tour.SpecialOffer;
import by.epam.shaturko.entity.tour.Tour;
import by.epam.shaturko.entity.user.User;
import by.epam.shaturko.service.ServiceCreateSpecialOffer;
import by.epam.shaturko.service.ServiceGettingData;
import by.epam.shaturko.service.ServiceProvider;
import by.epam.shaturko.service.exception.ServiceException;

public class GetChosenTours implements Command {
	private final static GetChosenTours INSTANCE = new GetChosenTours();
	private final static Logger logger = LogManager.getLogger();
	private final static String EMPTY_STRING = "";
	private final static String MIN_PRICE = "0";
	private final static String MAX_PRICE = "100000";
	private final static String CHOSEN = "chosen";
	private final static ServiceProvider PROVIDER = ServiceProvider.getInstance();
	private final static ServiceGettingData SERVICE_GETTING_DATA = PROVIDER.getServiceGettingData();
	private final static ServiceCreateSpecialOffer SERVICE_SO = PROVIDER.getServiceCreateSpecialOffer();

	private GetChosenTours() {
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession(true);
		String requestUrl = Constant.URL_TO_TOURS_PAGE;
		String priceFrom = request.getParameter(RequestParameter.PRICE_FROM);
		if (priceFrom.equals(EMPTY_STRING)) {
			priceFrom = MIN_PRICE;
		}
		String priceTo = request.getParameter(RequestParameter.PRICE_TO);
		if (priceTo.equals(EMPTY_STRING)) {
			priceTo = MAX_PRICE;
		}
		String dateOfDeparture = request.getParameter(RequestParameter.DEPARTURE_DATE);
		if (dateOfDeparture.equals(EMPTY_STRING)) {
			dateOfDeparture = LocalDate.now().plusDays(3).toString();
		}
		Map<String, Object> requestParameters = new HashMap<>();
		requestParameters.put(RequestParameter.DURATION_FROM, request.getParameter(RequestParameter.DURATION_FROM));
		requestParameters.put(RequestParameter.DURATION_TO, request.getParameter(RequestParameter.DURATION_TO));
		requestParameters.put(RequestParameter.FOOD, request.getParameter(RequestParameter.FOOD));
		requestParameters.put(RequestParameter.ROOM_TYPE, request.getParameter(RequestParameter.ROOM_TYPE));
		requestParameters.put(RequestParameter.COUNTRY_NAME, request.getParameter(RequestParameter.COUNTRY_NAME));
		requestParameters.put(RequestParameter.CATEGORY, request.getParameter(RequestParameter.CATEGORY));
		requestParameters.put(RequestParameter.NUMBER_OF_PEOPLE,
				request.getParameter(RequestParameter.NUMBER_OF_PEOPLE));
		Map<String, String> typesOfPlacement = (Map<String, String>) session
				.getAttribute(SessionAttribute.NUMBERS);
		requestParameters.put(RequestParameter.TYPE_OF_PLACEMENT,
				typesOfPlacement.get(request.getParameter(RequestParameter.NUMBER_OF_PEOPLE)));
		requestParameters.put(RequestParameter.DEPARTURE_DATE, dateOfDeparture);
		requestParameters.put(RequestParameter.PRICE_FROM, priceFrom);
		requestParameters.put(RequestParameter.PRICE_TO, priceTo);
		User user = (User) session.getAttribute(SessionAttribute.USER);
		try {
			List<Tour> toursList = SERVICE_GETTING_DATA.getListOfChosenTours(requestParameters, user);
			session.setAttribute(SessionAttribute.TOURS_LIST, toursList);
			session.setAttribute(SessionAttribute.TOURS_REQUEST, CHOSEN);
			List<SpecialOffer> offersList = SERVICE_SO.getAllSO();
			session.setAttribute(SessionAttribute.SPECIAL_OFFERS, offersList);
		} catch (ServiceException e) {
			logger.log(Level.ERROR, "Error while getting chosen tours from DB", e);
			session.setAttribute(SessionAttribute.ERROR_TYPE, Constant.ERROR_500);
			session.setAttribute(SessionAttribute.ERROR_MESSAGE, e);
			requestUrl = Constant.ERROR_COMMAND;
		}
		response.sendRedirect(requestUrl);
	}

	public static GetChosenTours getInstance() {
		return INSTANCE;
	}

}
