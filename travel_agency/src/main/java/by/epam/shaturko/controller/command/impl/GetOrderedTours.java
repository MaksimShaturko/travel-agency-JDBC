package by.epam.shaturko.controller.command.impl;

import java.io.IOException;
import java.util.List;

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
import by.epam.shaturko.entity.tour.Tour;
import by.epam.shaturko.entity.user.User;
import by.epam.shaturko.service.ServiceGettingData;
import by.epam.shaturko.service.ServiceProvider;
import by.epam.shaturko.service.exception.ServiceException;

public class GetOrderedTours implements Command {
	private final static GetOrderedTours INSTANCE = new GetOrderedTours();
	private final static Logger logger = LogManager.getLogger();
	private final static String ORDERED = "ordered";
	private final static int TEN = 10;
	private final static String EMPTY = "isEmpty";

	private GetOrderedTours() {
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession(true);
		String requestUrl = Constant.URL_TO_TOURS_PAGE;
		User user = (User) session.getAttribute(SessionAttribute.USER);
		ServiceProvider provider = ServiceProvider.getInstance();
		ServiceGettingData serviceGettingData = provider.getServiceGettingData();
		try {
			List<Tour> toursList = serviceGettingData.getListOfOrderedTours(user);
			if(!toursList.isEmpty()) {
			session.setAttribute(SessionAttribute.TOURS_LIST, toursList);
			session.setAttribute(SessionAttribute.TOURS_REQUEST, ORDERED);
			} else {
				session.setAttribute(SessionAttribute.TOURS_REQUEST, EMPTY);
			}
		} catch (ServiceException e) {
			logger.log(Level.ERROR, "Error while getting ordered tours from DB", e);
			session.setAttribute(SessionAttribute.ERROR_TYPE, Constant.ERROR_500);
			session.setAttribute(SessionAttribute.ERROR_MESSAGE, e);
			requestUrl = Constant.ERROR_COMMAND;
		}
		session.setAttribute(SessionAttribute.ON_ONE_PAGE, TEN);
		response.sendRedirect(requestUrl);
	}

	public static GetOrderedTours getInstance() {
		return INSTANCE;
	}
}
