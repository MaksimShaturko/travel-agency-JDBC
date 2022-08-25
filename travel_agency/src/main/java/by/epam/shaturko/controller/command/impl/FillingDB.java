package by.epam.shaturko.controller.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.shaturko.controller.Constant;
import by.epam.shaturko.controller.SessionAttribute;
import by.epam.shaturko.controller.command.Command;
import by.epam.shaturko.service.FillingDBService;
import by.epam.shaturko.service.ServiceProvider;
import by.epam.shaturko.service.exception.ServiceException;

public class FillingDB implements Command {
	private final static FillingDB INSTANCE = new FillingDB();
	private final static Logger logger = LogManager.getLogger();

	private FillingDB() {
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ServiceProvider serviceProvider = ServiceProvider.getInstance();
		FillingDBService fillingDBService = serviceProvider.getFillingDBService();
		HttpSession session = request.getSession(true);
		String requestUrl = (String) session.getAttribute(SessionAttribute.REQUEST_URL);
		try {
			fillingDBService.fillInCategories();
			fillingDBService.fillInCountries(Constant.COUNTRIES_SOURCE);
			fillingDBService.fillInCities(Constant.CITIES_SOURCE);
			fillingDBService.fillInHotels(Constant.HOTELS_SOURCE);
			fillingDBService.fillInCitiesHasCategories(Constant.CHC_SOURCE);
		} catch (ServiceException e) {
			logger.log(Level.ERROR, "Error while filling DB", e);
			session.setAttribute(SessionAttribute.ERROR_TYPE, Constant.ERROR_500);
			session.setAttribute(SessionAttribute.ERROR_MESSAGE, e);
			requestUrl = Constant.ERROR_COMMAND;
		}
		response.sendRedirect(requestUrl);
	}

	public static FillingDB getInstance() {
		return INSTANCE;
	}

}
