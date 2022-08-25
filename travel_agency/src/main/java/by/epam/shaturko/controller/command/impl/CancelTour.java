package by.epam.shaturko.controller.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.shaturko.bean.tour.Tour;
import by.epam.shaturko.bean.user.User;
import by.epam.shaturko.controller.Constant;
import by.epam.shaturko.controller.RequestParameter;
import by.epam.shaturko.controller.SessionAttribute;
import by.epam.shaturko.controller.command.Command;
import by.epam.shaturko.service.ServiceChangingData;
import by.epam.shaturko.service.ServiceProvider;
import by.epam.shaturko.service.exception.ServiceException;

public class CancelTour implements Command {
	private final static CancelTour INSTANCE = new CancelTour();
	private static final Logger logger = LogManager.getLogger();

	private CancelTour() {
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession();
		String requestUrl = (String) session.getAttribute(SessionAttribute.REQUEST_URL);
		Tour tour = (Tour) session.getAttribute(SessionAttribute.ORDERED_TOUR);
		User user = (User) session.getAttribute(SessionAttribute.USER);
		try {
			ServiceProvider provider = ServiceProvider.getInstance();
			ServiceChangingData serviceChangingData = provider.getServiceChangingData();
			serviceChangingData.cancelTour(tour, user);
		} catch (ServiceException e) {
			logger.log(Level.ERROR, "Error while trying to cancel the tour", e);
			session.setAttribute(SessionAttribute.ERROR_TYPE, Constant.ERROR_500);
			session.setAttribute(SessionAttribute.ERROR_MESSAGE, e);
			requestUrl = Constant.ERROR_COMMAND;
		}
		response.sendRedirect(requestUrl);
	}

	public static CancelTour getInstance() {
		return INSTANCE;
	}

}
