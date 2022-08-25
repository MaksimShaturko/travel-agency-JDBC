package by.epam.shaturko.controller.command.impl;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.shaturko.controller.Constant;
import by.epam.shaturko.controller.PagePath;
import by.epam.shaturko.controller.SessionAttribute;
import by.epam.shaturko.controller.command.Command;
import by.epam.shaturko.entity.tour.SpecialOffer;
import by.epam.shaturko.service.ServiceCreateSpecialOffer;
import by.epam.shaturko.service.ServiceGettingData;
import by.epam.shaturko.service.ServiceProvider;
import by.epam.shaturko.service.exception.ServiceException;

public class GoToCreatingSpecialOfferPage implements Command {
	private final static GoToCreatingSpecialOfferPage INSTANCE = new GoToCreatingSpecialOfferPage();
	private final static Logger logger = LogManager.getLogger();

	private GoToCreatingSpecialOfferPage() {
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		HttpSession session = request.getSession(true);
		session.setAttribute(SessionAttribute.REQUEST_URL, Constant.URL_TO_SO_PAGE);
		ServiceProvider provider = ServiceProvider.getInstance();
		ServiceGettingData serviceGettingData = provider.getServiceGettingData();
		ServiceCreateSpecialOffer serviceSO = provider.getServiceCreateSpecialOffer();
		try {
			List<SpecialOffer> offersList = serviceSO.getAllSO();
			session.setAttribute(SessionAttribute.SPECIAL_OFFERS, offersList);
		} catch (ServiceException e) {
			logger.log(Level.ERROR, "Error while getting all special offers", e);
			session.setAttribute(SessionAttribute.ERROR_TYPE, Constant.ERROR_500);
			session.setAttribute(SessionAttribute.ERROR_MESSAGE, e);
			response.sendRedirect(Constant.ERROR_COMMAND);
		}
		if (session.getAttribute(SessionAttribute.SHOW_COUNTRIES) == null) {
			session.setAttribute(SessionAttribute.SHOW_COUNTRIES, true);
			session.setAttribute(SessionAttribute.SHOW_CITIES, false);
			session.setAttribute(SessionAttribute.SHOW_HOTELS, false);
			try {
				List<String> listOfCountriesNames = serviceGettingData.getListOfCountriesNames();
				session.setAttribute(SessionAttribute.LIST_OF_COUNTRIES_NAMES, listOfCountriesNames);
			} catch (ServiceException e) {
				logger.log(Level.ERROR, "Error while getting list of countries names", e);
				session.setAttribute(SessionAttribute.ERROR_TYPE, Constant.ERROR_500);
				session.setAttribute(SessionAttribute.ERROR_MESSAGE, e);
				response.sendRedirect(Constant.ERROR_COMMAND);
			}
		}
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.CREATING_SO);
		requestDispatcher.forward(request, response);
	}

	public static GoToCreatingSpecialOfferPage getInstance() {
		return INSTANCE;
	}
}
